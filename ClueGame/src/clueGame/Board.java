package clueGame;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board
{

	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private int numRows;
	private int numColumns;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	private static Board theInstance = new Board();
	private Board()
	{	
		super();
	}
	public static Board getInstance()
    {
		return theInstance;
    }
	public void initialize()// throws BadConfigFormatException
	{
		ArrayList<String[]> l = new ArrayList<String[]>();
		try
		{
			loadSetupConfig();
			l = loadLayoutConfig();
		} catch (BadConfigFormatException e)
		{
			e.printStackTrace();
		}
    	buildBoard(l);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
//    	for (int row = 0; row < numRows; row++)
//		{
//			for (int col = 0; col < numColumns; col++)
//			{
//				System.out.print(grid[row][col].getInitial());
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	buildAdjLists();
	}
	private void buildBoard(ArrayList<String[]> arrList)
	{
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				
				char c = arrList.get(row)[col].charAt(0);
				BoardCell b = new BoardCell(row, col, c);
				
				if (roomMap.containsKey(c))
				{
					b.setIsRoom(true);
				}
				 
				if (arrList.get(row)[col].length() > 1)
				{
					char d = arrList.get(row)[col].charAt(1);
					Room r = getRoom(c);
					if (d == '*')
					{
						r.setCenterCell(b);
					}
					else if (d == '#')
					{
						r.setLabelCell(b);
					}
					else if (d == '<')
					{
						b.setIsDoorway(true);
						b.setDoorDirection(DoorDirection.LEFT);
					}
					else if (d == '>')
					{
						 b.setIsDoorway(true);
						 b.setDoorDirection(DoorDirection.RIGHT);
					}
					else if (d == '^')
					{
						b.setIsDoorway(true);
						b.setDoorDirection(DoorDirection.UP);
					}
					else if (d == 'v')
					{
						b.setIsDoorway(true);
						b.setDoorDirection(DoorDirection.DOWN);
					}
					else
					{
						b.setSecretPassage(d);
					}
				}
								 
				 grid[row][col] = b;
			}
		}
	}
	
	private void buildAdjLists()
	{
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				if (row != 0)
				{
					grid[row][col].addAdjacency(grid[row-1][col]);
				}
				if (row != numRows-1)
				{
					grid[row][col].addAdjacency(grid[row+1][col]);
				}
				if (col != 0)
				{
					grid[row][col].addAdjacency(grid[row][col-1]);
				}
				if (col != numColumns-1)
				{
					grid[row][col].addAdjacency(grid[row][col+1]);
				}

			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathlength)
	{
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps)
	{
		for (BoardCell adjCell : thisCell.getAdjList())
		{
			if (visited.contains(adjCell) || adjCell.isOccupied())
			{
				continue;
			}
			
			visited.add(adjCell);
			
			if (adjCell.isRoom() || (numSteps == 1 && !adjCell.isOccupied()))
			{
				targets.add(adjCell);
			}
			else
			{
				findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	public BoardCell getCell(int row, int col)
	{
		return grid[row][col];
	}
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile)
	{
		this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}
	public void loadSetupConfig() throws BadConfigFormatException
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(this.setupConfigFile));
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.charAt(0) != '/')
				{
					String[] row = line.split(", ");
					
					if (row[0].equals("Room"))
					{
						Room room = new Room(row[1]);
						roomMap.put(row[2].charAt(0), room);
					}
				}
			}
			br.close();
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
//			for (Map.Entry<Character, Room> entry : roomMap.entrySet())
//			{
//			    System.out.println(entry.getKey() + ":" + entry.getValue().getName());
//			}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
	}
	public ArrayList<String[]> loadLayoutConfig() throws BadConfigFormatException
	{
		ArrayList<String[]> l = new ArrayList<String[]>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(this.layoutConfigFile));
			String line;
			while ((line = br.readLine()) != null)
			{
				String[] row = line.split(",");
				l.add(row);
			}
			br.close();
			
			this.numRows = l.size();
			this.numColumns = l.get(0).length;
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
		return l;
	}
	public Room getRoom(char c)
	{
		return roomMap.get(c);
	}
	public Room getRoom(BoardCell cell)
	{
		char c = cell.getInitial();
		return getRoom(c);
	}
	public int getNumRows()
	{
		return this.numRows;
	}
	public int getNumColumns()
	{
		return this.numColumns;
	}
	
	public static void main(String[] args)
	{
		Board board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

	}

}

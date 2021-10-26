package cluegame;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	Map<Character, Room> roomMap;
	
	private static final String WALKWAY = "Walkway";
	
	//singleton design pattern
	private static Board theInstance = new Board();
	
	private Board()
	{	
		super();
	}
	public static Board getInstance()
    {
		return theInstance;
    }
	
	public void initialize()
	{
		//initialize a board
		List<String[]> list = new ArrayList<>();
		try
		{
			//load setup and config files
			//make roomMap and load board data into list
			loadSetupConfig();
			list = loadLayoutConfig();
		} 
		catch (BadConfigFormatException e)
		{
			e.printStackTrace();
		}
		//build board using board data from list
    	buildBoard(list);
    	//build adjacency list for all cells
    	buildAdjLists();
	}
	
	private void buildBoard(List<String[]> list)
	{
		//initialize new grid
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				//make board cell from data
				BoardCell boardCell = new BoardCell(row, col, list.get(row)[col].charAt(0));
				
				//if the cell value is 2 char long then its a room center, label, or a door
				if (list.get(row)[col].length() > 1)
				{
					char secondChar = list.get(row)[col].charAt(1);
					Room room = getRoom(boardCell.getInitial());
					//check whether room center, label, or door (+ direction)
					switch (secondChar)
					{
					case '*':
						room.setCenterCell(boardCell);
						boardCell.setRoomCenter(true);
						break;
					case '#':
						room.setLabelCell(boardCell);
						boardCell.setLabel(true);
						break;
					case '<':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.LEFT);
						break;
					case '>':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.RIGHT);
						break;
					case '^':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.DOWN);
						break;
					default:
						boardCell.setSecretPassage(secondChar);
						break;
					}
				}
				//add board cell to board
				grid[row][col] = boardCell;
			}
		}
	}
	
	private void buildAdjLists()
	{
		//initialize three board cell variables to use
		BoardCell currentCell;
		BoardCell centerCell;
		//iterate through grid
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				//set current cell to the cell at the current row and column
				currentCell = getCell(row,col);
	
				if (Boolean.TRUE.equals(currentCell.isSecretPassage()))
				{
					//get the center cell for the room to which the secret passage belongs
					centerCell = getRoom(currentCell).getCenterCell();
					//get the center cell for the room to which the secret passage leads
					BoardCell secretPassage = getRoom(currentCell.getSecretPassage()).getCenterCell();
					//add the destination of the passage to the center cell
					centerCell.addAdjacency(secretPassage);
				}
				if (getRoom(currentCell).getName().equals(WALKWAY))
				{
					//check up, down, left, right of current cell if not out of bounds
					if (row != 0 && getRoom(getCell(row-1,col)).getName().equals(WALKWAY))
					{
						//add [up] cell to current cell adjacency list
						currentCell.addAdjacency(getCell(row-1,col));
					}
					if (row != numRows-1 && getRoom(getCell(row+1,col)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row+1,col));
					}
					if (col != 0 && getRoom(getCell(row,col-1)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row,col-1));
					}
					if (col != numColumns-1 && getRoom(getCell(row,col+1)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row,col+1));
					}
					
					if (Boolean.TRUE.equals(currentCell.isDoorway()))
					{
						switch (getCell(row,col).getDoorDirection())
						{
						case UP:
							//get the center cell of the room [above] the current cell
							centerCell = getRoom(getCell(row-1,col)).getCenterCell();
							//add the center cell of the room to the adjacency list of the current cell
							currentCell.addAdjacency(centerCell);
							//ALSO add the current cell to the room's center cell adjacency list
							centerCell.addAdjacency(currentCell);
							break;
						case DOWN:
							centerCell = getRoom(getCell(row+1,col)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						case RIGHT:
							centerCell = getRoom(getCell(row,col+1)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						case LEFT:
							centerCell = getRoom(getCell(row,col-1)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathlength)
	{
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps)
	{
		for (BoardCell adjCell : thisCell.getAdjList())
		{
			if ( visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoomCenter()) )
			{
				continue;
			}
			
			visited.add(adjCell);
			
			if ( (numSteps == 1 && !adjCell.isOccupied()) || Boolean.TRUE.equals(adjCell.isRoomCenter()) )
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
	s
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile)
	{
		this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}
	
	public void loadSetupConfig() throws BadConfigFormatException
	{
		roomMap = new HashMap<>();
		try ( BufferedReader reader = new BufferedReader(new FileReader(this.setupConfigFile)) )
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				// a slash is a comment and should not be read in as data
				if (line.charAt(0) != '/')
				{
					String[] row = line.split(", ");
					//make a new room using the name given in the text file
					Room room = new Room(row[1]);
					//add to the roomMap with the char that represents the room as the key
					roomMap.put(row[2].charAt(0), room);
				}
			}
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
	}
	
	public List<String[]> loadLayoutConfig() throws BadConfigFormatException
	{
		List<String[]> list = new ArrayList<>();
		try ( BufferedReader reader = new BufferedReader(new FileReader(this.layoutConfigFile)) )
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				//get a row from the csv file and add it to the array list
				String[] row = line.split(",");
				list.add(row);
			}
			
			//set the rows and columns based on the retrieved data
			this.numRows = list.size();
			this.numColumns = list.get(0).length;
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
		return list;
	}
	
	public Room getRoom(char c) { return roomMap.get(c); }
	
	public Room getRoom(BoardCell cell)
	{
		char c = cell.getInitial();
		return getRoom(c);
	}
	
	public Set<BoardCell> getTargets() { return targets; }
	
	public int getNumRows() { return this.numRows; }
	
	public int getNumColumns() { return this.numColumns; }
	
	public BoardCell getCell(int row, int col) { return grid[row][col]; }
	
	public Set<BoardCell> getAdjList(int row, int col) { return getCell(row,col).getAdjList(); }
}

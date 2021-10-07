package clueGame;


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
	
	private static Board theInstance = new Board();
	private Board()
	{	
		super();
	}
	// this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize()
    {
    	buildBoard();
    	buildAdjLists();
    }
	private void buildBoard()
	{
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				grid[row][col] = new BoardCell(row, col);
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
			if (visited.contains(adjCell) || adjCell.getOccupied())
			{
				continue;
			}
			
			visited.add(adjCell);
			
			if (adjCell.isRoom() || (numSteps == 1 && !adjCell.getOccupied()))
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
		
	}
	public void loadLayoutConfig() throws BadConfigFormatException
	{
		
		
	}
	public Room getRoom(char c)
	{
		return new Room(null, null, null);
	}
	public Room getRoom(BoardCell cell)
	{
		return new Room(null, null, null);
	}
	public int getNumRows()
	{
		return this.numRows;
	}
	public int getNumColumns()
	{
		return this.numColumns;
	}
	

}

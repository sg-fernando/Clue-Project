package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
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
		grid = new BoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++)
		{
			for (int col = 0; col < COLS; col++)
			{
				grid[row][col] = new BoardCell(row, col);
			}
		}
	}
	
	private void buildAdjLists()
	{
		for (int row = 0; row < ROWS; row++)
		{
			for (int col = 0; col < COLS; col++)
			{
				if (row != 0)
				{
					grid[row][col].addAdjacency(grid[row-1][col]);
				}
				if (row != ROWS-1)
				{
					grid[row][col].addAdjacency(grid[row+1][col]);
				}
				if (col != 0)
				{
					grid[row][col].addAdjacency(grid[row][col-1]);
				}
				if (col != COLS-1)
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
	public void setConfigFiles(String csv, String text) {
		// TODO Auto-generated method stub
		
	}
	public void loadSetupConfig() {
		// TODO Auto-generated method stub
		
	}
	public void loadLayoutConfig() {
		// TODO Auto-generated method stub
		
	}
	public Object getRoom(char c) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

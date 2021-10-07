package experiment;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestBoard
{
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard()
	{
		//create board
		buildBoard();
		//create all adjacency lists
		buildAdjLists();
	}
	
	private void buildBoard()
	{
		grid = new TestBoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++)
		{
			for (int col = 0; col < COLS; col++)
			{
				grid[row][col] = new TestBoardCell(row, col);
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
	
	public void calcTargets(TestBoardCell startCell, int pathlength)
	{
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
	}
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps)
	{
		for (TestBoardCell adjCell : thisCell.getAdjList())
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
	
	public Set<TestBoardCell> getTargets()
	{
		return targets;
	}
	public TestBoardCell getCell(int row, int col)
	{
		return grid[row][col];
	}
}


/*
 * A constructor that sets up the board.
void calcTargets( TestBoardCell startCell, int pathlength) – calculates legal targets for a move from startCell of length pathlength.
Set<TestBoardCell> getTargets() – gets the targets last created by calcTargets()
Why not just return the list from calcTargets()? In the game, you will need the list of targets to highlight the board and to verify that the selected cell is valid. So better to calculate once and use a getter when needed.

TestBoardCell getCell( int row, int col ) – returns the cell from the board at row, col.

 */
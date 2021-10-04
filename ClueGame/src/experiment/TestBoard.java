package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard
{
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	public TestBoard()
	{
		
	}
	public void calcTargets(TestBoardCell startCell, int pathlength)
	{
		
	}
	public Set<TestBoardCell> getTargets()
	{
		return targets;
	}
	public TestBoardCell getCell(int row, int col)
	{
		return new TestBoardCell(row,col);
	}
}


/*
 * A constructor that sets up the board.
void calcTargets( TestBoardCell startCell, int pathlength) – calculates legal targets for a move from startCell of length pathlength.
Set<TestBoardCell> getTargets() – gets the targets last created by calcTargets()
Why not just return the list from calcTargets()? In the game, you will need the list of targets to highlight the board and to verify that the selected cell is valid. So better to calculate once and use a getter when needed.

TestBoardCell getCell( int row, int col ) – returns the cell from the board at row, col.

 */
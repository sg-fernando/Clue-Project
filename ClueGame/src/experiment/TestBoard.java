package experiment;

import java.util.Set;

public class TestBoard
{
	public TestBoard()
	{
		
	}
	public void calcTargets(TestBoardCell startCell, int pathlength)
	{
		
	}
	public Set<TestBoardCell> getTargets()
	{
		return null;
	}
	public TestBoardCell getCell(int row, int col)
	{
		return null;
	}
}


/*
 * A constructor that sets up the board.
void calcTargets( TestBoardCell startCell, int pathlength) – calculates legal targets for a move from startCell of length pathlength.
Set<TestBoardCell> getTargets() – gets the targets last created by calcTargets()
Why not just return the list from calcTargets()? In the game, you will need the list of targets to highlight the board and to verify that the selected cell is valid. So better to calculate once and use a getter when needed.

TestBoardCell getCell( int row, int col ) – returns the cell from the board at row, col.

 */
package tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp
{
	TestBoard board;
	@Before
	public void setUp()
	{
		board = new TestBoard();
	}

	@Test
	public void testAdjacency()
	{
		TestBoardCell cell;
		Set<TestBoardCell> testList;
		//top left corner
		cell = board.getCell(0, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0))); //down
		Assert.assertTrue(testList.contains(board.getCell(0, 1))); //right
		Assert.assertEquals(2,testList.size());
		
		//bottom right corner
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3))); //up
		Assert.assertTrue(testList.contains(board.getCell(3, 2))); //left
		Assert.assertEquals(2,testList.size());

		//left edge
		cell = board.getCell(1, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 0))); //up
		Assert.assertTrue(testList.contains(board.getCell(2, 0))); //down
		Assert.assertTrue(testList.contains(board.getCell(1, 1))); //right
		Assert.assertEquals(3,testList.size());
		
		//right edge
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3))); //up
		Assert.assertTrue(testList.contains(board.getCell(2, 3))); //down
		Assert.assertTrue(testList.contains(board.getCell(1, 2))); //left
		Assert.assertEquals(3,testList.size());
		
		//middle of grid
		cell = board.getCell(2, 2);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 2))); //up
		Assert.assertTrue(testList.contains(board.getCell(3, 2))); //down
		Assert.assertTrue(testList.contains(board.getCell(2, 1))); //left
		Assert.assertTrue(testList.contains(board.getCell(2, 3))); //right
		Assert.assertEquals(4,testList.size());
	}
	
	@Test
	public void testTargetsNormal()
	{
		TestBoardCell cell;
		Set<TestBoardCell> targets;
		
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 4);
		targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		
		cell = board.getCell(0, 0);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		
		cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
	}
	
	@Test
	public void testTargetsMixed()
	{
		TestBoardCell cell;
		Set<TestBoardCell> targets;
		
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
}


/*
Class BoardTestsExp (in tests) - JUnit test class. Includes:
@BeforeEach method to set up your BoardExp.
Methods to test the creation of adjacency lists for a 4x4 board, including:
top left corner (i.e., location [0][0])
bottom right corner (i.e., location [3][3])
a right edge (e.g., location [1][3])
a left edge (e.g., location [3][0])

Methods (minimum 5) to test target creation on a 4x4 board (see examples below)
Test for behavior on empty 4x4 board.
Test for behavior with at least one cell being flagged as occupied.  A player cannot move into an occupied cell.
Test for behavior with at least one cell being flagged as a room.   A player used up all movement points upon entering a room.
*/
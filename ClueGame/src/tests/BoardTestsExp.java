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
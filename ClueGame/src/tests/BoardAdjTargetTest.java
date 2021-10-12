package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@Before
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, Blanche's room that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(21, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 20)));
		assertTrue(testList.contains(board.getCell(9, 3)));
		
		// now test the Dorothy room (note not marked since multiple test here)
		testList = board.getAdjList(21, 4);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(24, 1)));
		
		// one more room, the garage
		testList = board.getAdjList(2, 20);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(6, 18)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(13, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 3)));
		assertTrue(testList.contains(board.getCell(13, 6)));
		assertTrue(testList.contains(board.getCell(14, 5)));
		
		testList = board.getAdjList(11, 22);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(11, 24)));
		assertTrue(testList.contains(board.getCell(12, 22)));
		assertTrue(testList.contains(board.getCell(11, 21)));
		assertTrue(testList.contains(board.getCell(10, 22)));
		
		testList = board.getAdjList(17, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(21, 12)));
		assertTrue(testList.contains(board.getCell(17, 12)));
		assertTrue(testList.contains(board.getCell(16, 13)));
		assertTrue(testList.contains(board.getCell(17, 14)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on narrow walkway piece
		Set<BoardCell> testList = board.getAdjList(7, 15);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(7, 14)));
		assertTrue(testList.contains(board.getCell(7, 16)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(17, 11);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 10)));
		assertTrue(testList.contains(board.getCell(16, 11)));
		assertTrue(testList.contains(board.getCell(17, 12)));

		// Test adjacent to walkways
		testList = board.getAdjList(13, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 7)));
		assertTrue(testList.contains(board.getCell(13, 9)));
		assertTrue(testList.contains(board.getCell(12, 8)));
		assertTrue(testList.contains(board.getCell(14, 8)));

		// Test next to closet
		testList = board.getAdjList(12,17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(13, 17)));
		assertTrue(testList.contains(board.getCell(12, 18)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInFrontYard() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 24), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 22)));
		assertTrue(targets.contains(board.getCell(12, 22)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 24), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(13, 22)));
		assertTrue(targets.contains(board.getCell(14, 22)));	
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(11, 20)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 24), 4);
		targets= board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(15, 22)));	
		assertTrue(targets.contains(board.getCell(8, 22)));
		assertTrue(targets.contains(board.getCell(12, 21)));	
	}
	
	@Test
	public void testTargetsInLounge() {
		// test a roll of 1
		board.calcTargets(board.getCell(3, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(11, 24)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(3, 13), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(7, 15)));	
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(8, 12)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(3, 13), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(8, 12)));	
		assertTrue(targets.contains(board.getCell(8, 11)));
		assertTrue(targets.contains(board.getCell(7, 16)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(6, 18), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(7, 18)));	
		assertTrue(targets.contains(board.getCell(6, 17)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 18), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(9, 18)));
		assertTrue(targets.contains(board.getCell(7, 16)));	
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(8, 17)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(6, 18), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(2, 20)));
		assertTrue(targets.contains(board.getCell(6, 20)));
		assertTrue(targets.contains(board.getCell(7, 15)));	
		assertTrue(targets.contains(board.getCell(7, 17)));
		assertTrue(targets.contains(board.getCell(10, 18)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(14, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(13, 6)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(14, 6), 3);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(9, 3)));
		assertTrue(targets.contains(board.getCell(15, 2)));
		assertTrue(targets.contains(board.getCell(17, 6)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(14, 6), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(9, 3)));
		assertTrue(targets.contains(board.getCell(15, 2)));
		assertTrue(targets.contains(board.getCell(21, 4)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(10, 19)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 19), 3);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 19)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(11, 22)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 19), 4);
		targets= board.getTargets();
		assertEquals(21, targets.size());
		assertTrue(targets.contains(board.getCell(11, 24)));
		assertTrue(targets.contains(board.getCell(12, 22)));
		assertTrue(targets.contains(board.getCell(15, 19)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(13, 19).setOccupied(true);
		board.calcTargets(board.getCell(11, 19), 4);
		board.getCell(13, 19).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(11, 17)));
		assertTrue(targets.contains(board.getCell(14, 18)));
		assertTrue(targets.contains(board.getCell(7, 19)));	
		assertFalse( targets.contains( board.getCell(13, 19))) ;
		assertFalse( targets.contains( board.getCell(15, 19))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(3, 13).setOccupied(true);
		board.getCell(7, 12).setOccupied(true);
		board.calcTargets(board.getCell(7, 13), 1);
		board.getCell(3, 13).setOccupied(false);
		board.getCell(7, 12).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 13)));	
		assertTrue(targets.contains(board.getCell(7, 14)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(17, 20).setOccupied(true);
		board.calcTargets(board.getCell(21, 21), 3);
		board.getCell(17, 20).setOccupied(false);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(9, 3)));

	}
}

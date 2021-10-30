package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cluegame.Board;
import cluegame.BoardCell;
import cluegame.Card;
import cluegame.Player;

public class gameSetupTests
{
	private static Board board;
	
	@Before
	public void setUp()
	{
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	@Test
	public void testDeck()
	{
		Set<Card> testDeck = board.getDeck();
		assertEquals(6+6+9, testDeck.size());
	}
	
	@Test
	public void testPlayers()
	{
		Set<Player> testPlayers = board.getPlayers();
		assertEquals(6, testPlayers.size());
		assertTrue()
	}
}

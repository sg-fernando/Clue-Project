package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cluegame.Board;
import cluegame.Card;
import cluegame.CardType;
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
	
	private Boolean checkPlayerName(Set<Player> players, String name)
	{
		for (Player p : players)
		{
			if (name.equals(p.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void testPlayers()
	{
		Set<Player> testPlayers = board.getPlayers();
		assertEquals(6, testPlayers.size());
		assertTrue(checkPlayerName(testPlayers, "Miles"));
		assertTrue(checkPlayerName(testPlayers, "Dorothy"));
		assertTrue(checkPlayerName(testPlayers, "Blanche"));
		assertTrue(checkPlayerName(testPlayers, "Rose"));
		assertTrue(checkPlayerName(testPlayers, "Sophia"));
		assertTrue(checkPlayerName(testPlayers, "Stan"));
		
		int humanCount = 0;
		int computerCount = 0;
		for (Player player : testPlayers)
		{
			if (player.isHuman())
			{
				humanCount++;
			}
			else
			{
				computerCount++;
			}
		}
		
		assertEquals(1, humanCount);
		assertEquals(5, computerCount);
	}
	
	private Boolean checkCardName(Set<Card> deck, String name)
	{
		for (Card card : deck)
		{
			if (name.equals(card.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	private int checkDeckType(Set<Card> deck, CardType type)
	{
		int count = 0;
		for (Card card : deck)
		{
			if (card.getType() == type)
			{
				count++;
			}
		}
		return count;
	}
	
	@Test
	public void testDeck()
	{
		Set<Card> testDeck = board.getDeck();
		assertEquals(6+6+9, testDeck.size());
		assertEquals(6, checkDeckType(testDeck, CardType.PLAYER));
		assertEquals(6, checkDeckType(testDeck, CardType.WEAPON));
		assertEquals(9, checkDeckType(testDeck, CardType.ROOM));
		//test player cards
		assertTrue(checkCardName(testDeck, "Stan"));
		assertTrue(checkCardName(testDeck, "Miles"));
		assertTrue(checkCardName(testDeck, "Dorothy"));
		assertTrue(checkCardName(testDeck, "Blanche"));
		assertTrue(checkCardName(testDeck, "Rose"));
		assertTrue(checkCardName(testDeck, "Sophia"));
		assertTrue(checkCardName(testDeck, "Stan"));
		//test weapon cards
		assertTrue(checkCardName(testDeck, "Bathrobe"));
		assertTrue(checkCardName(testDeck, "Magnum Research Micro Desert Eagle"));
		assertTrue(checkCardName(testDeck, "Lipstick"));
		assertTrue(checkCardName(testDeck, "Sophia's Purse"));
		assertTrue(checkCardName(testDeck, "Feathered Slipper"));
		assertTrue(checkCardName(testDeck, "Whipped Cream"));
		//test room cards
		assertTrue(checkCardName(testDeck, "Garage"));
		assertTrue(checkCardName(testDeck, "Lounge"));
		assertTrue(checkCardName(testDeck, "Sophia's Bedroom"));
		assertTrue(checkCardName(testDeck, "Rose's Bedroom"));
		assertTrue(checkCardName(testDeck, "Bathroom"));
		assertTrue(checkCardName(testDeck, "Dorothy's Bedroom"));
		assertTrue(checkCardName(testDeck, "Kitchen"));
		assertTrue(checkCardName(testDeck, "Blanche's Room"));
		assertTrue(checkCardName(testDeck, "Front Yard"));
	}
	
	@Test
	public void testDeal()
	{
		board.deal();
		Set<Card> testDeck = board.getDeck();
		assertEquals(0, testDeck.size());
		for (Player player : board.getPlayers())
		{
			assertEquals(3, player.getHand().size());
		}
	}
}

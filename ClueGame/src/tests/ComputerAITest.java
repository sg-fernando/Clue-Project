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
import cluegame.CardType;
import cluegame.ComputerPlayer;
import cluegame.Player;
import cluegame.Solution;

public class ComputerAITest
{
	private static Board board;
	
	private static Card milesCard = new Card("Player","Miles");
	private static Card dorothyCard = new Card("Player","Dorothy");
	private static Card blancheCard = new Card("Player","Blanche");
//	private static Card roseCard = new Card("Player","Rose");
//	private static Card sophiaCard = new Card("Player","Sophia");
//	private static Card stanCard = new Card("Player","Stan");
	
	private static Card garageCard = new Card("Room","Garage");
//	private static Card loungeCard = new Card("Room","Lounge");
//	private static Card sophRoomCard = new Card("Room","Sophia's Bedroom");
//	private static Card roseRoomCard = new Card("Room","Rose's Bedroom");
//	private static Card bathroomCard = new Card("Room","Bathroom");
//	private static Card dorRoomCard = new Card("Room","Dorothy's Bedroom");
//	private static Card kitchenCard = new Card("Room","Kitchen");
//	private static Card blanRoomCard = new Card("Room","Blanche's Room");
//	private static Card yardCard = new Card("Room","Front Yard");
	
	private static Card bathrobeCard = new Card("Weapon","Bathrobe");
	private static Card magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
	private static Card lipstickCard = new Card("Weapon","Lipstick");
//	private static Card purseCard = new Card("Weapon","Sophia's Purse");
//	private static Card slipperCard = new Card("Weapon","Feathered Slipper");
//	private static Card creamCard = new Card("Weapon","Whipped Cream");
	
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
	public void selectTargets()
	{
		Set<Card> testDeck = board.getDeck();
		ComputerPlayer testComputer = new ComputerPlayer("Computer", ' ', 2, 20, testDeck);
		
		Solution testSuggestion = testComputer.createSuggestion(board);
		//test room matches current location
		assertTrue(testSuggestion.getRoom().equals(garageCard));
		
		testDeck.clear();
		testDeck.add(bathrobeCard);
		testDeck.add(milesCard);
		
		testComputer.setUnseen(testDeck);
		testSuggestion = testComputer.createSuggestion(board);
		//test if only one weapon not seen, it's selected
		assertTrue(testSuggestion.getWeapon().equals(bathrobeCard));
		//test if only one person not seen, it's selected
		assertTrue(testSuggestion.getPerson().equals(milesCard));		
		
		testDeck.clear();
		testDeck.add(milesCard);
		testDeck.add(dorothyCard);
		testDeck.add(blancheCard);
		
		testDeck.add(bathrobeCard);
		testDeck.add(magnumCard);
		testDeck.add(lipstickCard);
		
		testComputer.setUnseen(testDeck);
		
		//test if multiple weapons not seen, one of them is randomly selected
		//test if multiple persons not seen, one of them is randomly selected		
		
		int count = 0;
		int milesCount = 0;
		int dorothyCount = 0;
		int blancheCount = 0;
		
		int bathrobeCount = 0;
		int magnumCount = 0;
		int lipstickCount = 0;
		while (count < 100)
		{
			count++;
			testSuggestion = testComputer.createSuggestion(board);
			if (testSuggestion.getPerson().equals(milesCard))
			{
				milesCount++;
			}
			else if (testSuggestion.getPerson().equals(dorothyCard))
			{
				dorothyCount++;
			}
			else if (testSuggestion.getPerson().equals(blancheCard))
			{
				blancheCount++;
			}
			
			if (testSuggestion.getWeapon().equals(bathrobeCard))
			{
				bathrobeCount++;
			}
			else if (testSuggestion.getWeapon().equals(magnumCard))
			{
				magnumCount++;
			}
			else if (testSuggestion.getWeapon().equals(lipstickCard))
			{
				lipstickCount++;
			}

		}
		assertTrue(milesCount > 0);
		assertTrue(dorothyCount > 0);
		assertTrue(blancheCount > 0);
		
		assertTrue(bathrobeCount > 0);
		assertTrue(magnumCount > 0);
		assertTrue(lipstickCount > 0);
	}

	@Test
	public void createSuggestion()
	{
		//test if no rooms in list, select randomly
		//test if room in list that has not been seen, select it
		//test if room in list that has been seen, each target (including room) selected randomly

	}
	
}

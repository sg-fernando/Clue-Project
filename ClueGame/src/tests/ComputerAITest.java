package tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cluegame.Board;
import cluegame.BoardCell;
import cluegame.Card;
import cluegame.ComputerPlayer;
import cluegame.Solution;

public class ComputerAITest
{
	private static Board board;
	
	private static Card milesCard = new Card("Player","Miles");
	private static Card dorothyCard = new Card("Player","Dorothy");
	private static Card blancheCard = new Card("Player","Blanche");
	
	private static Card garageCard = new Card("Room","Garage");
	
	private static Card bathrobeCard = new Card("Weapon","Bathrobe");
	private static Card magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
	private static Card lipstickCard = new Card("Weapon","Lipstick");
	
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
	public void createSuggestion()
	{
		Set<Card> testDeck = board.getDeck();
		ComputerPlayer testComputer = new ComputerPlayer("Computer", ' ', 2, 20);
		for (Card card : testDeck)
		{
			testComputer.updateUnseen(card);
		}
		
		Solution testSuggestion = testComputer.createSuggestion();
		//test room matches current location
		assertTrue(testSuggestion.getRoom().equals(garageCard));
		
		testDeck.clear();
		testDeck.add(bathrobeCard);
		testDeck.add(milesCard);
		
		testComputer.setUnseen(testDeck);
		testSuggestion = testComputer.createSuggestion();
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
			testSuggestion = testComputer.createSuggestion();
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
		
		
		//test solution card not in hand
		testDeck.clear();
		testDeck.add(milesCard);
		testDeck.add(dorothyCard);
		testDeck.add(bathrobeCard);
		testDeck.add(magnumCard);
		testComputer.setUnseen(testDeck);
		testComputer.updateHand(milesCard);
		testComputer.updateHand(bathrobeCard);
		testSuggestion = testComputer.createSuggestion();
		assertTrue(testSuggestion.getPerson().equals(dorothyCard));
		assertTrue(testSuggestion.getWeapon().equals(magnumCard));
	}

	@Test
	public void selectTargets()
	{
		Set<Card> testDeck = board.getDeck();
		ComputerPlayer testComputer = new ComputerPlayer("Computer", ' ', 8, 19);
		for (Card card : testDeck)
		{
			testComputer.updateUnseen(card);
		}
		
		BoardCell testTarget;

		//test if no rooms in list, select randomly
		
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		int other = 0;
		int count = 0;
		board.setRoll(1);
		while (count < 1000)
		{
			testTarget = testComputer.selectTarget();
			if (testTarget.getRow() == 7 && testTarget.getColumn() == 19)
			{
				up++;
			}
			else if (testTarget.getRow() == 9 && testTarget.getColumn() == 19)
			{
				down++;
			}
			else if (testTarget.getRow() == 8 && testTarget.getColumn() == 18)
			{
				left++;
			}

			else if (testTarget.getRow() == 8 && testTarget.getColumn() == 20)
			{
				right++;
			}
			else
			{
				other++;
			}
			count++;
		}
		assertTrue(up > 0);
		assertTrue(down > 0);
		assertTrue(left > 0);
		assertTrue(right > 0);
		assertTrue(other == 0);

		
		testComputer.setRow(6);
		testComputer.setColumn(18);
		//test if room in list that has not been seen, select it
		testDeck.clear();
		testDeck.add(garageCard);
		testComputer.setUnseen(testDeck);
		testTarget = testComputer.selectTarget();
		assertTrue(board.getRoom(testTarget).getName().equals(garageCard.getName()));
		
		//test if room in list that has been seen, each target (including room) selected randomly
		testDeck.clear();
		testComputer.setUnseen(testDeck);
		testComputer.updateSeen(garageCard);
		
		up = 0;
		down = 0;
		left = 0;
		right = 0;
		other = 0;
		count = 0;
		while (count < 100)
		{
			testTarget = testComputer.selectTarget();
			if (testTarget.getRow() == 2 && testTarget.getColumn() == 20)
			{
				up++;
			}
			else if (testTarget.getRow() == 7 && testTarget.getColumn() == 18)
			{
				down++;
			}
			else if (testTarget.getRow() == 6 && testTarget.getColumn() == 17)
			{
				left++;
			}

			else if (testTarget.getRow() == 6 && testTarget.getColumn() == 19)
			{
				right++;
			}
			else
			{
				other++;
			}
			count++;
		}
		assertTrue(up > 0);
		assertTrue(down > 0);
		assertTrue(left > 0);
		assertTrue(right > 0);
		assertTrue(other == 0);
	}
	
}

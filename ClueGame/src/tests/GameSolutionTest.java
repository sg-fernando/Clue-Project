package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import cluegame.Board;
import cluegame.Card;
import cluegame.ComputerPlayer;
import cluegame.Player;
import cluegame.Solution;

public class GameSolutionTest
{
	private static Board board;
	
	// card constants for tests
	
	private static Card milesCard = new Card("Player","Miles");
	private static Card dorothyCard = new Card("Player","Dorothy");
	private static Card blancheCard = new Card("Player","Blanche");
//	private static Card roseCard = new Card("Player","Rose");
//	private static Card sophiaCard = new Card("Player","Sophia");
	private static Card stanCard = new Card("Player","Stan");
	
	private static Card garageCard = new Card("Room","Garage");
	private static Card loungeCard = new Card("Room","Lounge");
	private static Card sophRoomCard = new Card("Room","Sophia's Bedroom");
//	private static Card roseRoomCard = new Card("Room","Rose's Bedroom");
//	private static Card bathroomCard = new Card("Room","Bathroom");
//	private static Card dorRoomCard = new Card("Room","Dorothy's Bedroom");
//	private static Card kitchenCard = new Card("Room","Kitchen");
//	private static Card blanRoomCard = new Card("Room","Blanche's Room");
	private static Card yardCard = new Card("Room","Front Yard");
	
	private static Card bathrobeCard = new Card("Weapon","Bathrobe");
	private static Card magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
	private static Card lipstickCard = new Card("Weapon","Lipstick");
//	private static Card purseCard = new Card("Weapon","Sophia's Purse");
//	private static Card slipperCard = new Card("Weapon","Feathered Slipper");
	private static Card creamCard = new Card("Weapon","Whipped Cream");
	
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
	public void testAccusation()
	{
		Solution test, test2, test3, test4;
		// correct values
		test = new Solution(milesCard, garageCard, bathrobeCard);
		// incorrect player
		test2 = new Solution(dorothyCard, garageCard, bathrobeCard);
		// incorrect room
		test3 = new Solution(milesCard, loungeCard, bathrobeCard);
		// incorrect weapon
		test4 = new Solution(milesCard, garageCard, magnumCard);
		
		// setting default values (test solution) as the board solution
		board.setSolution(test);
		// ensuring true is returned if values match
		assertTrue(board.checkAccusation(test));
		// and ensuring false is returned if any value is off
		assertFalse(board.checkAccusation(test2));
		assertFalse(board.checkAccusation(test3));
		assertFalse(board.checkAccusation(test4));
	}
	@Test
	public void disproveSuggestion()
	{	
		ComputerPlayer p1 = new ComputerPlayer("Miles", 'g', 0, 7, null);
		HashSet<Card> hand = new HashSet<>();
		hand.add(milesCard);
		hand.add(garageCard);
		hand.add(bathrobeCard);
		p1.setHand(hand);
		
		ComputerPlayer p2 = new ComputerPlayer("Dorothy", 'p', 0, 16, null);
		HashSet<Card> hand2 = new HashSet<>();
		hand2.add(dorothyCard);
		hand2.add(loungeCard);
		hand2.add(magnumCard);
		p2.setHand(hand2);
		
		ComputerPlayer p3 = new ComputerPlayer("Blanche", 'r', 7, 23, null);
		HashSet<Card> hand3 = new HashSet<>();
		hand3.add(blancheCard);
		hand3.add(sophRoomCard);
		hand3.add(lipstickCard);
		p3.setHand(hand3);
		
		Card suggestion = milesCard;
		Card suggestion2 = loungeCard;
		Card suggestion3 = lipstickCard;
		
		Solution solutionTest = new Solution(suggestion, suggestion2, suggestion3);
		// ensuring true is returned if values match
		// player matches
		assertTrue(p1.disproveSuggestion(solutionTest) != null);
//		// room matches
		assertTrue(p2.disproveSuggestion(solutionTest) != null);
//		// weapon matches
		assertTrue(p3.disproveSuggestion(solutionTest) != null);
		

		
		solutionTest = new Solution(stanCard, yardCard, creamCard);
		// and false otherwise (i.e. null is returned)
		// if no cards in player hand match input
		assertFalse(p1.disproveSuggestion(solutionTest) != null);
		assertFalse(p2.disproveSuggestion(solutionTest) != null);
		assertFalse(p3.disproveSuggestion(solutionTest) != null);
		

		
		// and finally checking for random return:
		solutionTest = new Solution(milesCard, garageCard, bathrobeCard);
		HashSet<Card> test = new HashSet<>();
		for(int i = 0; i < 20; i++) {
			test.add(p1.disproveSuggestion(solutionTest));
			// elements of test are unique by properties
			// of a hashset; so, if it gets to the same size
			// as the hand in question, we know all 3
			// unique cards must be returned by disproveSuggestion
			if(test.size() == hand.size()) {
				assert(true);
			} else if(i == 19) assert(false);
		}
	}
	
	@Test
	public void handleSuggestion()
	{
		Card suggestion = stanCard;
		Card suggestion2 = yardCard;
		Card suggestion3 = creamCard;
		
		Solution solutionTest = new Solution(suggestion, suggestion2, suggestion3);
		
		HashSet<Player> pSet = new HashSet<>();
		
		ComputerPlayer p1 = new ComputerPlayer("Miles", 'g', 0, 7, null);
		HashSet<Card> hand = new HashSet<>();
		hand.add(milesCard);
		hand.add(garageCard);
		hand.add(bathrobeCard);
		p1.setHand(hand);
		
		ComputerPlayer p2 = new ComputerPlayer("Dorothy", 'p', 0, 16, null);
		HashSet<Card> hand2 = new HashSet<>();
		hand2.add(dorothyCard);
		hand2.add(loungeCard);
		hand2.add(magnumCard);
		p2.setHand(hand2);
		
		ComputerPlayer p3 = new ComputerPlayer("Blanche", 'r', 7, 23, null);
		HashSet<Card> hand3 = new HashSet<>();
		hand3.add(blancheCard);
		hand3.add(sophRoomCard);
		hand3.add(lipstickCard);
		p3.setHand(hand3);
		
		pSet.add(p1);
		pSet.add(p2);
		pSet.add(p3);
		
		board.setPlayers(pSet);
		
		// handle suggestion iterates through
		// pSet
		// no one can disprove; null returned
		assertTrue(board.handleSuggestion(solutionTest, p1) == null);
		
//		// accusing player gets away with it (returns a card if
//		// p1 is not the accusing player parameter)
		solutionTest = new Solution(suggestion, garageCard, suggestion3);
		assertTrue(board.handleSuggestion(solutionTest, p1) == null);
//		// ensures p2 disproves p1, p3 disproves p2.
		assertTrue(board.handleSuggestion(solutionTest, p2).equals(garageCard));
		solutionTest = new Solution(dorothyCard, suggestion2, suggestion3);
		assertTrue(board.handleSuggestion(solutionTest, p3).equals(dorothyCard));
		
	}
	
}

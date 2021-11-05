package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
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

public class GameSolutionTest
{
	private static Board board;
	private static Card milesCard, dorothyCard, blancheCard,
	roseCard, sophiaCard, stanCard, garageCard, loungeCard,
	sophRoomCard, roseRoomCard, bathroomCard, dorRoomCard,
	kitchenCard, blanRoomCard, yardCard, bathrobeCard, 
	magnumCard, lipstickCard, purseCard, slipperCard, creamCard;
	
	@Before
	public void setUp()
	{
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		// card constants for tests
		
		milesCard = new Card("Player","Miles");
		dorothyCard = new Card("Player","Dorothy");
		blancheCard = new Card("Player","Blanche");
		roseCard = new Card("Player","Rose");
		sophiaCard = new Card("Player","Sophia");
		stanCard = new Card("Player","Stan");
		
		garageCard = new Card("Room","Garage");
		loungeCard = new Card("Room","Lounge");
		sophRoomCard = new Card("Room","Sophia's Bedroom");
		roseRoomCard = new Card("Room","Rose's Bedroom");
		bathroomCard = new Card("Room","Bathroom");
		dorRoomCard = new Card("Room","Dorothy's Bedroom");
		kitchenCard = new Card("Room","Kitchen");
		blanRoomCard = new Card("Room","Blanche's Room");
		yardCard = new Card("Room","Front Yard");
		
		bathrobeCard = new Card("Weapon","Bathrobe");
		magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
		lipstickCard = new Card("Weapon","Lipstick");
		purseCard = new Card("Weapon","Sophia's Purse");
		slipperCard = new Card("Weapon","Feathered Slipper");
		creamCard = new Card("Weapon","Whipped Cream");
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
		ComputerPlayer p = new ComputerPlayer("Miles", 'g', 0, 7);
		HashSet<Card> hand = new HashSet<>();
		hand.add(milesCard);
		hand.add(garageCard);
		hand.add(bathrobeCard);
		p.setHand(hand);
		
		ComputerPlayer p2 = new ComputerPlayer("Dorothy", 'p', 0, 16);
		HashSet<Card> hand2 = new HashSet<>();
		hand2.add(dorothyCard);
		hand2.add(loungeCard);
		hand2.add(bathrobeCard);
		p2.setHand(hand2);
		
		ComputerPlayer p3 = new ComputerPlayer("Blanche", 'r', 7, 23);
		HashSet<Card> hand3 = new HashSet<>();
		hand3.add(milesCard);
		hand3.add(loungeCard);
		hand3.add(magnumCard);
		p3.setHand(hand3);
		
		// ensuring true is returned if values match
		// player matches
		assertTrue(p.disproveSuggestion(milesCard) != null);
		// room matches
		assertTrue(p2.disproveSuggestion(loungeCard) != null);
		// weapon matches
		assertTrue(p3.disproveSuggestion(magnumCard) != null);
		
		// and false otherwise (i.e. null is returned)
		// if no cards in player hand match input
		assertFalse(p.disproveSuggestion(magnumCard) != null);
		assertFalse(p2.disproveSuggestion(garageCard) != null);
		assertFalse(p3.disproveSuggestion(dorothyCard) != null);
		
		// and finally checking for random return:
		boolean a, b, c;
		a = false;
		b = false;
		c = false;
		for(int i = 0; i < 50; i++) {
			HashSet<Card> test = new HashSet<>();
			test.add(p.disproveSuggestion(milesCard));
			test.add(p.disproveSuggestion(garageCard));
			test.add(p.disproveSuggestion(bathrobeCard));
			if(test.size() == hand.size()) {
				assert(true);
			} else if(i == 49) assert(false);
		}
		
		
		
	}
	
	@Test
	public void handleSuggestion()
	{
		Card suggestion = creamCard;
		Card suggestion2 = garageCard;
		Card suggestion3 = dorothyCard;
		
		HashSet<Player> pSet = new HashSet<>();
		
		ComputerPlayer p1 = new ComputerPlayer("Miles", 'g', 0, 7);
		HashSet<Card> hand = new HashSet<>();
		hand.add(milesCard);
		hand.add(garageCard);
		hand.add(bathrobeCard);
		p1.setHand(hand);
		
		ComputerPlayer p2 = new ComputerPlayer("Dorothy", 'p', 0, 16);
		HashSet<Card> hand2 = new HashSet<>();
		hand2.add(dorothyCard);
		hand2.add(loungeCard);
		hand2.add(bathrobeCard);
		p2.setHand(hand2);
		
		ComputerPlayer p3 = new ComputerPlayer("Blanche", 'r', 7, 23);
		HashSet<Card> hand3 = new HashSet<>();
		hand3.add(milesCard);
		hand3.add(loungeCard);
		hand3.add(magnumCard);
		p3.setHand(hand3);
		
		pSet.add(p1);
		pSet.add(p2);
		pSet.add(p3);
		
		board.setPlayers(pSet);
		
		// handle suggestion iterates through
		// pSet
		// no one can disprove; null returned
		assertTrue(board.handleSuggestion(suggestion, p1) == null);
		// accusing player gets away with it (returns a card if
		// p1 is not the accusing player parameter)
		assertTrue(board.handleSuggestion(suggestion2, p1) == null);
		// ensures p2 disproves p1, p3 disproves p2.
		assertTrue(board.handleSuggestion(suggestion2, p2) == garageCard);
		assertTrue(board.handleSuggestion(suggestion3, p3) == dorothyCard);
		
	}
	
}

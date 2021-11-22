package GUI;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.Board;
import cluegame.Card;
import cluegame.Player;

public class GameCardsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel handPeoplePanel;
	private JPanel handRoomsPanel;
	private JPanel handWeaponsPanel;
	
	private JPanel seenPeoplePanel;
	private JPanel seenRoomsPanel;
	private JPanel seenWeaponsPanel;

	public GameCardsPanel()
	{
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel panel = createUpper();
		add(panel);
		panel = createMid();
		add(panel);
		panel = createLower();
		add(panel);
	}
	
	private JPanel createUpper()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		handPeoplePanel = new JPanel();
		handPeoplePanel.setLayout(new GridLayout(0,1));
		handPeoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		
		seenPeoplePanel = new JPanel();
		seenPeoplePanel.setLayout(new GridLayout(0,1));
		seenPeoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		
		panel.add(handPeoplePanel);
		panel.add(seenPeoplePanel);
		
		return panel;
	}
	
	private JPanel createMid()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		handRoomsPanel = new JPanel();
		handRoomsPanel.setLayout(new GridLayout(0,1));
		handRoomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		
		seenRoomsPanel = new JPanel();
		seenRoomsPanel.setLayout(new GridLayout(0,1));
		seenRoomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		
		panel.add(handRoomsPanel);
		panel.add(seenRoomsPanel);
		
		return panel;
	}
	private JPanel createLower()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		handWeaponsPanel = new JPanel();
		handWeaponsPanel.setLayout(new GridLayout(0,1));
		handWeaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		
		seenWeaponsPanel = new JPanel();
		seenWeaponsPanel.setLayout(new GridLayout(0,1));
		seenWeaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		
		panel.add(handWeaponsPanel);
		panel.add(seenWeaponsPanel);
		
		return panel;
	}
	
	public void updateHand(Card card)
	{
		JTextField text = new JTextField(15);
		text.setEditable(false);
		text.setText(card.getName());
		
		switch (card.getType())
		{
		case PLAYER:
			handPeoplePanel.add(text);
			break;
		case ROOM:
			handRoomsPanel.add(text);
			break;
		case WEAPON:
			handWeaponsPanel.add(text);
			break;
		default:
			break;
		}
	}
	
	public void updateSeen(Card card, Player player)
	{
		JTextField text = new JTextField(15);
		text.setEditable(false);
		text.setText(card.getName());
		text.setBackground(player.getColor());
		
		switch (card.getType())
		{
		case PLAYER:
			seenPeoplePanel.add(text);
			break;
		case ROOM:
			seenRoomsPanel.add(text);
			break;
		case WEAPON:
			seenWeaponsPanel.add(text);
			break;
		default:
			break;
		}
	}
		
//	public static void main(String[] args)
//	{
//		// creating arbitrary player to use for
//		// displaying cards in hand
//		ComputerPlayer cpu = new ComputerPlayer( "Col. Mustard", 'o', 0, 0);
//		// cards in hand
//		Card milesCard = new Card("Player","Miles");
//		Card stanCard = new Card("Player","Stan");
//		Card loungeCard = new Card("Room","Lounge");
//		Card lipstickCard = new Card("Weapon","Lipstick");
//		Card bathrobeCard = new Card("Weapon","Bathrobe");
//		Card creamCard = new Card("Weapon","Whipped Cream");
//		Card slipperCard = new Card("Weapon","Feathered Slipper");
//		// seen cards
//		Card dorothyCard = new Card("Player","Dorothy");
//		Card blancheCard = new Card("Player","Blanche");
//		Card roseCard = new Card("Player","Rose");
//		Card garageCard = new Card("Room","Garage");
//		Card yardCard = new Card("Room","Front Yard");
//		Card magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
//		// assembling decks
//		HashSet<Card> hand = new HashSet<>();
//		hand.add(milesCard);
//		hand.add(stanCard);
//		hand.add(loungeCard);
//		hand.add(lipstickCard);
//		hand.add(creamCard);
//		hand.add(bathrobeCard);
//		hand.add(slipperCard);
//		cpu.setHand(hand);
//		
//		HashSet<Card> seen = new HashSet<>();
//		seen.add(dorothyCard);
//		seen.add(blancheCard);
//		seen.add(roseCard);
//		seen.add(garageCard);
//		seen.add(yardCard);
//		seen.add(magnumCard);
//		cpu.setSeen(seen);
//		
//		GameCardsPanel panel = new GameCardsPanel();  // create the panel
//		
//		for (Card card : cpu.getSeen())
//		{
//			panel.updateSeen(card);
//		}
//		for (Card card : cpu.getHand())
//		{
//			panel.updateHand(card);
//		}
//		
//		
//		JFrame frame = new JFrame();  // create the frame 
//		frame.setContentPane(panel); // put the panel in the frame
//		frame.setSize(220, 750);  // size the frame
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
//		frame.setVisible(true); // make it visible
//	}
}

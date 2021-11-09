package GUI;

import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.Card;
import cluegame.CardType;
import cluegame.ComputerPlayer;
import cluegame.Player;

public class GameCardsPanel extends JPanel
{
	
//	private JTextField handPersonText;
//	private JTextField seenPersonText;
//	private JTextField handRoomText;
//	private JTextField seenRoomText;
//	private JTextField handWeaponText;
//	private JTextField seenWeaponText;
	
	private JPanel seenPeoplePanel;
	private JPanel seenRoomsPanel;
	private JPanel seenWeaponsPanel;

	public GameCardsPanel(Player player)
	{
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel panel = createUpper(player);
		add(panel);
		panel = createMid(player);
		add(panel);
		panel = createLower(player);
		add(panel);
	}
	
	private JPanel createUpper(Player player)
	{
		JTextField handPersonText;
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0,1));
		handPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		for(Card it : player.getHand())
		{
			if(it.getType() == CardType.PLAYER)
			{
				handPersonText = new JTextField(15);
				handPersonText.setEditable(false);
				handPersonText.setText(it.getName());
				handPanel.add(handPersonText);
			}
		}
		
		seenPeoplePanel = new JPanel();
		seenPeoplePanel.setLayout(new GridLayout(0,1));
		seenPeoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		for(Card it : player.getSeen())
		{
			if(it.getType() == CardType.PLAYER)
			{
				updateSeen(it);
			}
		}
		
		panel.add(handPanel);
		panel.add(seenPeoplePanel);
		
		return panel;
	}
	
	private JPanel createMid(Player player)
	{
		JTextField handRoomText;

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0,1));
		handPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		for(Card it : player.getHand())
		{
			if(it.getType() == CardType.ROOM)
			{
				handRoomText = new JTextField(15);
				handRoomText.setEditable(false);
				handRoomText.setText(it.getName());
				handPanel.add(handRoomText);
			}
		}
		
		seenRoomsPanel = new JPanel();
		seenRoomsPanel.setLayout(new GridLayout(0,1));
		seenRoomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		for(Card it : player.getSeen())
		{
			if(it.getType() == CardType.ROOM)
			{
				updateSeen(it);
			}
		}
		
		panel.add(handPanel);
		panel.add(seenRoomsPanel);
		
		return panel;
	}
	private JPanel createLower(Player player)
	{
		JTextField handWeaponText;
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0,1));
		handPanel.setBorder(new TitledBorder (new EtchedBorder(), "In Hand:"));
		for(Card it : player.getHand())
		{
			if(it.getType() == CardType.WEAPON)
			{
				handWeaponText = new JTextField(15);
				handWeaponText.setEditable(false);
				handWeaponText.setText(it.getName());
				handPanel.add(handWeaponText);
			}
		}
		
		seenWeaponsPanel = new JPanel();
		seenWeaponsPanel.setLayout(new GridLayout(0,1));
		seenWeaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Seen:"));
		for(Card it : player.getSeen())
		{
			if(it.getType() == CardType.WEAPON)
			{
				updateSeen(it);
			}
		}
		
		panel.add(handPanel);
		panel.add(seenWeaponsPanel);
		
		return panel;
	}
	
	
	public void updateSeen(Card card)
	{
		JTextField text = new JTextField(15);
		text.setEditable(false);
		text.setText(card.getName());
		
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
		
	public static void main(String[] args)
	{
		// creating arbitrary player to use for
		// displaying cards in hand
		ComputerPlayer cpu = new ComputerPlayer( "Col. Mustard", 'o', 0, 0, null);
		// cards in hand
		Card milesCard = new Card("Player","Miles");
		Card stanCard = new Card("Player","Stan");
		Card loungeCard = new Card("Room","Lounge");
		Card lipstickCard = new Card("Weapon","Lipstick");
		Card bathrobeCard = new Card("Weapon","Bathrobe");
		Card creamCard = new Card("Weapon","Whipped Cream");
		Card slipperCard = new Card("Weapon","Feathered Slipper");
		// seen cards
		Card dorothyCard = new Card("Player","Dorothy");
		Card blancheCard = new Card("Player","Blanche");
		Card roseCard = new Card("Player","Rose");
		Card garageCard = new Card("Room","Garage");
		Card yardCard = new Card("Room","Front Yard");
		Card magnumCard = new Card("Weapon","Magnum Research Micro Desert Eagle");
		// assembling decks
		HashSet<Card> hand = new HashSet<>();
		hand.add(milesCard);
		hand.add(stanCard);
		hand.add(loungeCard);
		hand.add(lipstickCard);
		hand.add(creamCard);
		hand.add(bathrobeCard);
		hand.add(slipperCard);
		cpu.setHand(hand);
		
		HashSet<Card> seen = new HashSet<>();
		seen.add(dorothyCard);
		seen.add(blancheCard);
		seen.add(roseCard);
		seen.add(garageCard);
		seen.add(yardCard);
		seen.add(magnumCard);
		cpu.setSeen(seen);
		
		GameCardsPanel panel = new GameCardsPanel(cpu);  // create the panel
		
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(220, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}

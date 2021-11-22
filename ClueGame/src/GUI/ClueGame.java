package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cluegame.Board;
import cluegame.BoardCell;
import cluegame.Card;
import cluegame.CardType;
import cluegame.HumanPlayer;
import cluegame.Solution;

public class ClueGame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private static ClueGame frame = new ClueGame();
	
	private HumanPlayer humanPlayer;
	private Board board;
	
	private GameCardsPanel cardsPanel;
	private GameControlPanel controlPanel;
	
	private JLabel roomChoice;
	JComboBox<String> playerChoice;
	JComboBox<String> weaponChoice;

	private class Mouse implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (Boolean.FALSE.equals(board.isUnfinishedTurn()))
			{
				return;
			}
			BoardCell cell;
			int column = e.getX()/(board.getWidth()/board.getNumColumns());
			int row = e.getY()/(board.getHeight()/board.getNumRows());
			cell = board.getCell(row, column);
			if (Boolean.FALSE.equals(board.isTarget(cell)))
			{
				JOptionPane.showMessageDialog(frame, "That is not a target", "Message", JOptionPane.WARNING_MESSAGE);
				return;
			}
			humanPlayer.newPosition(cell);
			board.setUnfinished(false);
			if (!board.getRoom(cell).getName().equals(Board.WALKWAY))
			{
				suggestionDialog(board.getRoom(cell).getName());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

	}
	
	public void suggestionDialog(String roomName)
	{
		JOptionPane jop = new JOptionPane();
		final JDialog dialog = jop.createDialog(ClueGame.getInstance(), "Make a Suggestion");
		
		String[] players = getNames(CardType.PLAYER);
		String[] weapons = getNames(CardType.WEAPON);
		
		roomChoice = new JLabel(roomName);
		playerChoice = new JComboBox<>(players);
		weaponChoice = new JComboBox<>(weapons);

        JLabel roomsLabel = new JLabel("Room");
        JLabel playersLabel = new JLabel("Person");
        JLabel weaponsLabel = new JLabel("Weapon");
 
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener()
		{
	         public void actionPerformed(ActionEvent e)
	         {
	        	 Card player = new Card("Player", (String)playerChoice.getSelectedItem());
	        	 Card room = new Card("Room", roomChoice.getText());
	        	 Card weapon = new Card("Weapon", (String)weaponChoice.getSelectedItem());
	        	 dialog.dispose();
	        	 Solution suggestion = new Solution(player, room, weapon);
	        	 Card r = board.handleSuggestion(suggestion, humanPlayer);
	        	 controlPanel.setGuess(suggestion.getPerson().getName()+ ", "+suggestion.getRoom().getName()+", "+suggestion.getWeapon().getName(),board.getCurrentPlayer());
	        	 if (r != null)
	     		{
	     			board.getCurrentPlayer().updateSeen(r);
	     			cardsPanel.updateSeen(r,board.getDisprovenPlayer());
	     			controlPanel.setGuessResult(r.getName(),board.getDisprovenPlayer());
	     		}
	        	 board.setUnfinished(false);
	         }
	      });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		board.setUnfinished(true);
        		dialog.dispose();
        	}
	      });
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,2));
 
        p.add(roomsLabel);
        p.add(roomChoice);
        p.add(playersLabel);
        p.add(playerChoice);
        p.add(weaponsLabel);
        p.add(weaponChoice);
        
        p.add(submitButton);
        p.add(cancelButton);
 
		
		dialog.setContentPane(p);
		dialog.setVisible(true);
	}

	private ClueGame()
	{
		super();
	}

	public static ClueGame getInstance()
    {
		return frame;
    }
	
	public void initialize()
	{
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		board.deal();
		Mouse m = new Mouse();
		board.addMouseListener(m);
		humanPlayer = board.getHuman();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1000, 900);
		
		cardsPanel = new GameCardsPanel();
		cardsPanel.setPreferredSize(new Dimension(frame.getWidth()/5,0));
		for (Card card : humanPlayer.getHand())
		{
			cardsPanel.updateHand(card);
		}
		
		controlPanel = new GameControlPanel();
		controlPanel.setPreferredSize(new Dimension(0, frame.getHeight()/7));
		
		frame.add(board, BorderLayout.CENTER);
		frame.add(cardsPanel, BorderLayout.EAST);
		frame.add(controlPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	public static String[] getNames(CardType type)
	{
		String[] names = new String[9];
		int i = 0;
		for (Card card : Board.getInstance().getDeck())
		{
			if (card.getType() == type)
			{
				names[i] = card.getName();
				i++;
			}
		}
		
		return names;
	}
	
	
	public void splashScreen()
	{
		JLabel label = new JLabel("<html><center>You are "+ humanPlayer.getName()+"<br>Can you find the solution<br>before the Computer players?");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(frame, label, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void win()
	{
		JOptionPane.showMessageDialog(ClueGame.getInstance(), "You win!", "Message", JOptionPane.DEFAULT_OPTION);
		frame.dispose();
	}
	public static void lose()
	{
		JOptionPane.showMessageDialog(ClueGame.getInstance(), "Sorry, not correct. You lose.", "Message", JOptionPane.DEFAULT_OPTION);
		frame.dispose();
	}

	public static void main(String[] args)
	{
		ClueGame frame = ClueGame.getInstance();
		frame.initialize();
		frame.splashScreen();
	}



}
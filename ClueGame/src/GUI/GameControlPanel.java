package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.Board;
import cluegame.BoardCell;
import cluegame.Card;
import cluegame.CardType;
import cluegame.ComputerPlayer;
import cluegame.Player;
import cluegame.Solution;

public class GameControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField turnText;
	private JTextField rollText;
	private JTextField guessText;
	private JTextField guessResultText;
	
	private JButton next;
	private JButton makeAccusation;
	
	private Board board;
	
	private JComboBox<String> roomChoice;
	private JComboBox<String> playerChoice;
	private JComboBox<String> weaponChoice;
	
	public GameControlPanel()
	{
		setLayout(new GridLayout(2,0));
		JPanel panel = createUpper();
		add(panel);
		panel = createLower();
		add(panel);
		board = Board.getInstance();
	}
	
	private void nextButton()
	{
		if (Boolean.TRUE.equals(board.isUnfinishedTurn()))
		{
			JOptionPane.showMessageDialog(ClueGame.getInstance(), "Please finish you turn!", "Message", JOptionPane.WARNING_MESSAGE);		
		}
		else
		{
			setGuess("", new ComputerPlayer("", 'x', 0, 0));
			setGuessResult("", new ComputerPlayer("", 'x', 0, 0));
			board.updateCurrentPlayer();
			board.roll();
			setTurn(board.getCurrentPlayer(), board.getRoll());
			if (Boolean.TRUE.equals(board.getCurrentPlayer().equals(board.getHuman())))
			{
				humanTurn();
			}
			else
			{
				computerTurn();
			}
		}
	}
	
	private void humanTurn()
	{
		board.setUnfinished(true);
		if (Boolean.TRUE.equals(board.getCurrentPlayer().canSuggest()))
		{
			board.getCurrentPlayer().setSuggest(false);
			ClueGame.getInstance().suggestionDialog(board.getRoom(board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn())).getName());
		}
		if (Boolean.TRUE.equals(board.isUnfinishedTurn()))
		{
			BoardCell cell = board.getCell(board.getHuman().getRow(), board.getHuman().getColumn());
			board.calcTargets(cell, board.getRoll());
			board.revalidate();
			board.repaint();
		}
	}
	
	private void computerTurn()
	{
		Solution accusation = ((ComputerPlayer)board.getCurrentPlayer()).getAccusation();
		if (accusation != null && Boolean.TRUE.equals(board.checkAccusation(accusation)))
		{
			ClueGame.lose();
		}
		else if (Boolean.TRUE.equals(board.getCurrentPlayer().canSuggest()))
		{
			board.getCurrentPlayer().setSuggest(false);
			computerSuggest();
		}
		else
		{
			BoardCell target = ((ComputerPlayer) board.getCurrentPlayer()).selectTarget();
		
			board.getCurrentPlayer().newPosition(target);
			if (!board.getRoom(target).getName().equals(Board.WALKWAY))
			{
				computerSuggest();
			}
		}
	}
	private void computerSuggest()
	{
		Solution suggestion = ((ComputerPlayer)board.getCurrentPlayer()).createSuggestion();
		setGuess(suggestion.getPerson().getName()+ ", "+suggestion.getRoom().getName()+", "+suggestion.getWeapon().getName(),board.getCurrentPlayer());
		Card r = board.handleSuggestion(suggestion, board.getCurrentPlayer());
		if (r != null)
		{
			((ComputerPlayer)board.getCurrentPlayer()).removeUnseen(r);
			board.getCurrentPlayer().updateSeen(r);
			setGuessResult("Suggestion disproven!",board.getDisprovenPlayer());
		}
		board.revalidate();
		board.repaint();
	}
	
	private void makeAccusationButton()
	{
		if (Boolean.TRUE.equals(board.isUnfinishedTurn()))
		{
			JOptionPane jop = new JOptionPane();
			final JDialog dialog = jop.createDialog(ClueGame.getInstance(), "Make an Accusation");
			
			String[] rooms = ClueGame.getNames(CardType.ROOM);
			String[] players = ClueGame.getNames(CardType.PLAYER);
			String[] weapons = ClueGame.getNames(CardType.WEAPON);
			
			roomChoice = new JComboBox<>(rooms);
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
		        	 Card room = new Card("Room", (String)roomChoice.getSelectedItem());
		        	 Card weapon = new Card("Weapon", (String)weaponChoice.getSelectedItem());
		        	 dialog.dispose();
		        	 if (Boolean.TRUE.equals(board.checkAccusation(player, room, weapon)))
		        	 {
		        		 ClueGame.win();
		        	 }
		        	 else
		        	 {
		        		 ClueGame.lose();
		        	 }
		         }
		      });

	        JButton cancelButton = new JButton("Cancel");
	        cancelButton.addActionListener(new ActionListener()
			{
		         public void actionPerformed(ActionEvent e)
		         {
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
		else
		{
			JOptionPane.showMessageDialog(ClueGame.getInstance(), "It is not your turn!");
		}
	}
	
	private JPanel createUpper()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,4));
		
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		turnText = new JTextField(15);
		turnText.setEditable(false);
		turnPanel.add(turnLabel);
		turnPanel.add(turnText);
		
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll: ");
		rollText = new JTextField(5);
		rollText.setEditable(false);
		rollPanel.add(rollLabel);
		rollPanel.add(rollText);
		
		makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ActionListener()
		{
	         public void actionPerformed(ActionEvent e)
	         {
	        	 makeAccusationButton();
	         }
	      });

		next = new JButton("NEXT!");		
		next.addActionListener(new ActionListener()
		{
	         public void actionPerformed(ActionEvent e)
	         {
	        	 nextButton();
	         }
	      });

		
		panel.add(turnPanel);
		panel.add(rollPanel);
		panel.add(makeAccusation);
		panel.add(next);
		
		return panel;
	}
	
	private JPanel createLower()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessText = new JTextField();
		guessText.setEditable(false);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessPanel.add(guessText);
		
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessResultText = new JTextField();
		guessResultText.setEditable(false);
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResultPanel.add(guessResultText);
		
		panel.add(guessPanel);
		panel.add(guessResultPanel);
		return panel;
	}
	
	private void setTurn(Player player, int roll)
	{
		turnText.setText(player.getName());
		turnText.setBackground(player.getColor());
		rollText.setText(Integer.toString(roll));
	}
	
	public void setGuess(String string, Player player)
	{
		guessText.setText(string);
		guessText.setBackground(player.getColor());
	}
	
	public void setGuessResult(String string, Player player)
	{
		guessResultText.setText(string);
		guessResultText.setBackground(player.getColor());
	}
	
//	public static void main(String[] args)
//	{
//		GameControlPanel panel = new GameControlPanel();  // create the panel
//		JFrame frame = new JFrame();  // create the frame 
//		frame.setContentPane(panel); // put the panel in the frame
//		frame.setSize(750, 180);  // size the frame
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
//		frame.setVisible(true); // make it visible
//		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer( "Col. Mustard", 'o', 0, 0, null), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
//	}


}

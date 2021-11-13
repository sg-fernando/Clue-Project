package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.Board;
import cluegame.BoardCell;
import cluegame.ComputerPlayer;
import cluegame.Player;

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
		BoardCell cell = board.getCell(board.getHuman().getRow(), board.getHuman().getColumn());
		board.calcTargets(cell, board.getRoll());
		board.revalidate();
		board.repaint();
	}
	
	private void computerTurn()
	{
		BoardCell target = ((ComputerPlayer) board.getCurrentPlayer()).selectTarget();
		board.getCurrentPlayer().newPosition(target);
		board.revalidate();
		board.repaint();
	
	}
	
	private void makeAccusationButton()
	{
		JOptionPane.showMessageDialog(ClueGame.getInstance(), "make accusation");
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
	
	private void setGuess(String string)
	{
		guessText.setText(string);
	}
	
	private void setGuessResult(String string)
	{
		guessResultText.setText(string);
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

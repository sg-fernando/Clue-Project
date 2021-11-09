package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.Board;

public class ClueGame extends JFrame
{
	private static ClueGame frame = new ClueGame();

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1000, 800);	
		// Create the JPanel and add it to the JFrame
		JPanel controlPanel = new GameControlPanel();
		frame.add(controlPanel);
		JPanel cardsPanel = new GameCardsPanel();
		frame.add(cardsPanel);
		// Now let's view it
		frame.setVisible(true);
	}

	
	public static void main(String[] args)
	{
		// Create a JFrame with all the normal functionality
		ClueGame frame = ClueGame.getInstance();
		frame.initialize();
	}


}
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import cluegame.Board;
import cluegame.Card;
import cluegame.HumanPlayer;

public class ClueGame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static ClueGame frame = new ClueGame();
	
	private HumanPlayer humanPlayer;

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
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		board.deal();
		humanPlayer = board.getHuman();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1000, 900);
		
		GameCardsPanel cardsPanel = new GameCardsPanel();
		cardsPanel.setPreferredSize(new Dimension(frame.getWidth()/5,0));
		for (Card card : humanPlayer.getHand())
		{
			cardsPanel.updateHand(card);
		}
		
		GameControlPanel controlPanel = new GameControlPanel();
		controlPanel.setPreferredSize(new Dimension(0, frame.getHeight()/7));
		
		frame.add(board, BorderLayout.CENTER);
		frame.add(cardsPanel, BorderLayout.EAST);
		frame.add(controlPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	public void splashScreen()
	{
		JLabel label = new JLabel("<html><center>You are "+ humanPlayer.getName()+"<br>Can you find the solution<br>before the Computer players?");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(frame, label, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}
	
	public static void main(String[] args)
	{
		ClueGame frame = ClueGame.getInstance();
		frame.initialize();
		frame.splashScreen();
	}


}
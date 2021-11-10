package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
//		frame.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
		
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		frame.add(board, BorderLayout.CENTER);
		
		JPanel cardsPanel = new GameCardsPanel();
		frame.add(cardsPanel, BorderLayout.EAST);
		
		JPanel controlPanel = new GameControlPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}

	
	public static void main(String[] args)
	{
		// Create a JFrame with all the normal functionality
		ClueGame frame = ClueGame.getInstance();
		frame.initialize();
	}


}
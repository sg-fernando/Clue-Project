package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cluegame.Board;
import cluegame.Card;

public class ClueGame extends JFrame
{
	private static final long serialVersionUID = 1L;
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
		frame.setSize(1000, 900);
		
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
		frame.add(board, BorderLayout.CENTER);
		
		GameCardsPanel cardsPanel = new GameCardsPanel();
		for (Card card : board.getHuman().getHand())
		{
			cardsPanel.updateHand(card);
		}
		frame.add(cardsPanel, BorderLayout.EAST);
		
		GameControlPanel controlPanel = new GameControlPanel();
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
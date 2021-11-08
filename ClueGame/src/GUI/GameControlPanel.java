package GUI;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluegame.ComputerPlayer;

public class GameControlPanel extends JPanel
{

	private JTextField turnText;
	private JTextField rollText;
	private JTextField guessText;
	private JTextField guessResultText;

	public GameControlPanel()
	{
		setLayout(new GridLayout(2,0));
		JPanel panel = createUpper();
		add(panel);
		panel = createLower();
		add(panel);
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
		
		JButton makeAccusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		
		
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
	
	private void setTurn(ComputerPlayer computerPlayer, int roll)
	{
		turnText.setText(computerPlayer.getName());
		turnText.setBackground(computerPlayer.getColor());
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
	
	public static void main(String[] args)
	{
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 'o', 0, 0, null), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}


}

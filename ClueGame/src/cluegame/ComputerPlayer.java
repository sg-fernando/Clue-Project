package cluegame;

import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player
{

	private Set<Card> unseen;
	
	public ComputerPlayer(String name, char color, int row, int column, Set<Card> unseen)
	{
		super(name, color, false, row, column);
		this.unseen = unseen;
	}
	
	@Override
	public void updateHand(Card card)
	{
		super.updateHand(card);
		removeUnseen(card);
	}
	
	private void removeUnseen(Card card)
	{
		unseen.remove(card);
	}
	
	private int countType(CardType type)
	{
		int count = 0;
		for (Card card : unseen)
		{
			if (card.getType() == type)
			{
				count++;
			}
		}
		return count;
	}
	
	public Solution createSuggestion(Board board)
	{
		int player = 0;
		int weapon = 0;
		int randP = new Random().nextInt(countType(CardType.PLAYER));
		int randW = new Random().nextInt(countType(CardType.WEAPON));
		
		Card playerCard = null;
		Card roomCard = null;
		Card weaponCard = null;
		String room = board.getRoom(board.getCell(getRow(), getColumn())).getName();
	
		for (Card card : unseen)
		{
			if (card.getType() == CardType.PLAYER)
			{
				if (player == randP)
				{
					playerCard = card;
				}
				player++;
			}
			else if (card.getType() == CardType.WEAPON)
			{
				if (weapon == randW)
				{
					weaponCard = card;
				}
				weapon++;
			}
			else if (card.getName().equals(room))
			{
				roomCard = card;
			}
		}
		
		
		Solution solution = new Solution(playerCard, roomCard, weaponCard);
		
		return solution;
	}
	public BoardCell selectTarget(Board board, int roll)
	{
		BoardCell cell = board.getCell(getRow(), getColumn());
		Set<BoardCell> targets = board.calcTargets(cell, roll);
		int rand = new Random().nextInt(targets.size());
		int count = 0;
		for (BoardCell c : targets)
		{
			if (count == rand)
			{
				return c;
			}
			count++;
		}
		return null;
	}
	
	public void setUnseen(Set<Card> unseen)
	{
		this.unseen = unseen;
	}
}

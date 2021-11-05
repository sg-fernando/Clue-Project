package cluegame;

import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player
{

	private Set<Card> unseen;
	
	public ComputerPlayer(String name, char color, int row, int column) {
		super(name, color, false, row, column);
		removeUnseen();
	}
	
	private void removeUnseen()
	{
		Set<Card> hand = getHand();
		for (Card card : hand)
		{
			unseen.remove(card);
		}
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
	
	public Solution createSuggestion()
	{
		int player = 0;
		int room = 0;
		int weapon = 0;
		int randP = new Random().nextInt(countType(CardType.PLAYER));
		int randR = new Random().nextInt(countType(CardType.ROOM));
		int randW = new Random().nextInt(countType(CardType.WEAPON));
		
		Card playerCard = null;
		Card roomCard = null;
		Card weaponCard = null;
	
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
			else if (card.getType() == CardType.ROOM)
			{
				if (room == randR)
				{
					roomCard = card;
				}
				room++;
			}
			else if (card.getType() == CardType.WEAPON)
			{
				if (weapon == randW)
				{
					weaponCard = card;
				}
				weapon++;
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

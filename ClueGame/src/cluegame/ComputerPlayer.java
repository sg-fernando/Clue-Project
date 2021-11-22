package cluegame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player
{

	private Set<Card> unseen;
	private Board board;
	
	public ComputerPlayer(String name, char color, int row, int column)
	{
		super(name, color, false, row, column);
		unseen = new HashSet<Card>();
		board = Board.getInstance();
	}
	
	@Override
	public void updateHand(Card card)
	{
		super.updateHand(card);
		removeUnseen(card);
		
	}
	
	public void updateUnseen(Card card)
	{
		unseen.add(card);
	}

	public void removeUnseen(Card card)
	{
		unseen.remove(card);
	}
	
	
	private int countType(Set<Card> set, CardType type)
	{
		int count = 0;
		for (Card card : set)
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
		int weapon = 0;
		int randP = new Random().nextInt(countType(unseen, CardType.PLAYER));
		int randW = new Random().nextInt(countType(unseen, CardType.WEAPON));
		
		Card playerCard = null;
		Card roomCard = null;
		Card weaponCard = null;
		String room = board.getRoom(board.getCell(getRow(), getColumn())).getName();
		roomCard = new Card("Room", room);
	
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
		}
		
		return new Solution(playerCard, roomCard, weaponCard);
	}
	
	public Solution getAccusation()
	{
		Solution accusation = null;
		Card player = null;
		Card room = null;
		Card weapon = null;
		if (unseen.size() == 3)
		{
			for (Card c : unseen)
			{
				if (c.getType() == CardType.PLAYER)
				{
					player = c;
				}
				else if (c.getType() == CardType.ROOM)
				{
					room = c;
				}
				else
				{
					weapon = c;
				}
			}
		}
		accusation = new Solution(player, room, weapon);
		
		return accusation;
	}
	
	
	public BoardCell selectTarget()
	{
		BoardCell target = null;
		BoardCell currentCell = board.getCell(getRow(), getColumn());
		Set<BoardCell> targets = board.calcTargets(currentCell, board.getRoll());
		if (targets.size() < 1)
		{
			return board.getCell(getRow(), getColumn());
		}
		int rand = new Random().nextInt(targets.size());
		int count = 0;
		for (BoardCell cell : targets)
		{
			for (Card card : unseen)
			{
				if (card.getType() == CardType.ROOM && card.getName().equals(board.getRoom(cell).getName()))
				{
					return cell;
				}
			}
			if (count == rand)
			{
				target = cell;
			}
			count++;
		}
		return target;
	}
	
	public void setUnseen(Set<Card> unseen)
	{
		this.unseen = unseen;
	}
}

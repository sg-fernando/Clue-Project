package cluegame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player
{
	private String name;
	private Color color;
	private Boolean isHuman;
	private int row;
	private int column;
	private Set<Card> hand;
	private Set<Card> seen;
	
	protected Player(String name, char c, Boolean isHuman, int row, int column)
	{
		this.hand = new HashSet<>();
		this.name = name;
		this.isHuman = isHuman;
		this.row = row;
		this.column = column;
		switch (c)
		{
		case 'g':
			color = Color.GREEN;
			break;
		case 'p':
			color = Color.MAGENTA;
			break;
		case 'r':
			color = Color.RED;
			break;
		case 'w':
			color = Color.WHITE;
			break;
		case 'b':
			color = Color.BLUE;
			break;
		case 'y':
			color = Color.YELLOW;
			break;
		default:
			color = Color.BLACK;
			break;
		}
	}
	
	public Card disproveSuggestion(Solution suggestion)
	{
		int amount = 0;
		for (Card c : hand)
		{
			if (Boolean.TRUE.equals(suggestion.contains(c)))
			{
				amount++;
			}
		}
		if (amount == 1)
		{
			for (Card c : hand)
			{
				if (Boolean.TRUE.equals(suggestion.contains(c)))
				{
					return c;
				}
			}
		}
		if (amount > 1)
		{
			int rand = new Random().nextInt(hand.size());
			int count = 0;
			for (Card c : hand)
			{
				if (Boolean.TRUE.equals(suggestion.contains(c)))
				{
					if (count == rand)
					{
						return c;
					}
					count++;
				}
			}
		}
		return null;
	}
	
	public void updateHand(Card card)
	{
		hand.add(card);
	}
	
	public void updateSeen(Card seenCard)
	{
		seen.add(seenCard);
	}
	
	public Set<Card> getHand() { return hand; }
	public String getName() { return name; }
	public Color getColor() { return color; }
	public Boolean isHuman() { return isHuman; }
	

	//testing methods
	public void setHand(Set<Card> hand)
	{
		this.hand = hand;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}

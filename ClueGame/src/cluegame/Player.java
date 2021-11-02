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
	
	public Card disproveSuggestion(Card suggestion)
	{
		int amount = 0;
		for (Card c : hand)
		{
			if (suggestion.equals(c))
			{
				amount++;
			}
		}
		
		if (amount == 1)
		{
			
		}
		
		return null;
		
		int rand = new Random().nextInt();
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


}

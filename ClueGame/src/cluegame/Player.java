package cluegame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.awt.Graphics;

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
		this.seen = new HashSet<>();
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
		case 'o':
			color = Color.ORANGE;
			break;
		default:
			color = Color.BLACK;
			break;
		}
	}
	
	public void draw(int width, int height, Graphics g)
	{
		int y = height * row;
		int x = width * column;
		g.setColor(color);
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
	}
	public void newPosition(BoardCell cell)
	{
		Board.getInstance().getCell(getRow(), getColumn()).setOccupied(false);
		setRow(cell.getRow());
		setColumn(cell.getColumn());
		cell.setOccupied(true);
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
	public Set<Card> getSeen() { return seen; }
	public String getName() { return name; }
	public Color getColor() { return color; }
	public Boolean isHuman() { return isHuman; }
	
	public void setRow(int row)
	{
		this.row = row;
	}
	public void setColumn(int column)
	{
		this.column = column;
	}
	

	//testing methods
	public void setHand(Set<Card> hand)
	{
		this.hand = hand;
	}
	
	public void setSeen(Set<Card> seen)
	{
		this.seen = seen;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Boolean equals(Player other)
	{
		return (this.name.equals(other.getName()) && this.color == other.getColor() && this.isHuman == other.isHuman());
	}
}

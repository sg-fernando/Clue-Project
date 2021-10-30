package cluegame;

import java.awt.Color;

public class Player
{
	private String name;
	private Color color;
	private Boolean isHuman;
	private BoardCell currentCell;
	
	protected Player(String name, char c, Boolean isHuman)
	{
		this.name = name;
		this.isHuman = isHuman;
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
	
	
	public String getName() { return name; }
	public Color getColor() { return color; }
	public Boolean isHuman() { return isHuman; }


}

package cluegame;

import java.util.HashSet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

public class BoardCell
{
	private int row;
	private int col;
	
	private Boolean isOccupied = false;
	private Boolean isRoomCenter = false;
	private Boolean isLabel = false;
	private Boolean isDoorway = false;
	private Boolean isSecretPassage = false;
	
	private Set<BoardCell> adjList = new HashSet<>();

	private char secretPassage;
	private char initial;

	private DoorDirection doorDirection = DoorDirection.NONE;
	
	public BoardCell(int row, int col,char initial)
	{
		this.row = row;
		this.col = col;
		this.initial = initial;
	}
	
	public void draw(int width, int height, int xOffset, int yOffset, Graphics g, Room room)
	{
		if (Boolean.TRUE.equals(Board.getInstance().isTarget(this)) && Boolean.TRUE.equals(Board.getInstance().isUnfinishedTurn()))
		{
			g.setColor(Color.CYAN);
			g.fillRect(xOffset, yOffset, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(xOffset, yOffset, width, height);
		}
		else if (room.getName().equals(Board.WALKWAY))
		{
			g.setColor(Color.YELLOW.darker());
			g.fillRect(xOffset, yOffset, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(xOffset, yOffset, width, height);
		}
		else if (room.getName().equals(Board.UNUSED))
		{
			g.setColor(Color.BLACK);
			g.fillRect(xOffset, yOffset, width, height);
		}
		else if (Boolean.TRUE.equals(isLabel))
		{
			g.setColor(Color.BLUE);
			String[] name = room.getName().split(" ");
			int tempY = yOffset;
			for (String word : name)
			{
				g.drawString(word, xOffset, tempY);
				tempY += height/2;
			}
		}
		
		int doorHeight = height/4;
		int doorWidth = width/4;
		g.setColor(Color.BLUE);
		switch (doorDirection)
		{
		case NONE:
			break;
		case UP:
			g.fillRect(xOffset, yOffset-doorHeight, width, doorHeight);
			break;
		case DOWN:
			g.fillRect(xOffset, yOffset+height, width, doorHeight);
			break;
		case LEFT:
			g.fillRect(xOffset-doorWidth, yOffset, doorWidth, height);
			break;
		case RIGHT:
			g.fillRect(xOffset+width, yOffset, doorWidth, height);
			break;
		default:
			break;
        }
	}
	
	public char getInitial() { return this.initial; }
	
	public int getRow() { return this.row; }
	
	public int getCol() { return this.col; }
	
	public Set<BoardCell> getAdjList() { return adjList; }
	public void addAdjacency(BoardCell cell)
	{
		adjList.add(cell);
	}
	
	public Boolean isOccupied() { return this.isOccupied; }
	public void setOccupied(Boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}

	public Boolean isRoomCenter() { return this.isRoomCenter; }
	public void setRoomCenter(Boolean isRoomCenter)
	{
		this.isRoomCenter = isRoomCenter;
	}
	
	public Boolean isLabel() { return this.isLabel; }
	public void setLabel(Boolean isLabel)
	{
		this.isLabel = isLabel;
	}

	public Boolean isDoorway() { return this.isDoorway; }
	public void setDoorway(Boolean isDoorway)
	{
		this.isDoorway = isDoorway;
	}
	
	public char getSecretPassage() { return this.secretPassage; }
	public void setSecretPassage(char secretPassage)
	{
		this.secretPassage = secretPassage;
		this.isSecretPassage = true;
	}
	
	public Boolean isSecretPassage() { return this.isSecretPassage; }

	public DoorDirection getDoorDirection() { return this.doorDirection; }
	public void setDoorDirection(DoorDirection doorDirection)
	{
		this.doorDirection = doorDirection;
	}
	
	public Boolean equals(BoardCell other)
	{
		return (other.getRow() == this.row && other.getCol() == this.col);
	}
}

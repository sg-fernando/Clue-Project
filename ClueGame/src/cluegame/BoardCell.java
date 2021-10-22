package cluegame;

import java.util.HashSet;
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
}

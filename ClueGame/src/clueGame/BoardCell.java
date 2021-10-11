package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell
{
	private int row, col;
	
	private Boolean isRoom = false, isOccupied = false, isRoomCenter = false, isLabel = false, isDoorway = false;
	
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	private char secretPassage, initial;
	
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
	
	public Boolean isRoom() { return this.isRoom; }
	public void setIsRoom(boolean isRoom)
	{
		this.isRoom = isRoom;
	}
	
	public Boolean isOccupied() { return this.isOccupied; }
	public void setIsOccupied(Boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}

	public Boolean isRoomCenter() { return this.isRoomCenter; }
	public void setIsRoomCenter(Boolean isRoomCenter)
	{
		this.isRoomCenter = isRoomCenter;
	}
	
	public Boolean isLabel() { return this.isLabel; }
	public void setIsLabel(Boolean isLabel)
	{
		this.isLabel = isLabel;
	}

	public Boolean isDoorway() { return this.isDoorway; }
	public void setIsDoorway(Boolean isDoorway)
	{
		this.isDoorway = isDoorway;
	}
	
	public char getSecretPassage() { return this.secretPassage; }
	public void setSecretPassage(char secretPassage)
	{
		this.secretPassage = secretPassage;
	}

	public DoorDirection getDoorDirection() { return this.doorDirection; }
	public void setDoorDirection(DoorDirection doorDirection)
	{
		this.doorDirection = doorDirection;
	}
}

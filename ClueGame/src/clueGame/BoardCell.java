package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell
{

	private int row, col;
	
	private Boolean isRoom, isOccupied, isRoomCenter, isLabel, isDoorway;
	

	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	private char secretPassage, initial;
	
	private DoorDirection doorDirection = DoorDirection.NONE;
	
	public BoardCell(int row, int col,char initial)
	{
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.isRoom = false;
		this.isOccupied = false;
	}
	
	public void addAdjacency(BoardCell cell)
	{
		adjList.add(cell);
	}
	public Set<BoardCell> getAdjList()
	{
		return adjList;
	}
	public void setIsRoom(boolean isRoom)
	{
		this.isRoom = isRoom;
	}
	public Boolean isRoom()
	{
		return this.isRoom;
	}
	
	public void setIsOccupied(boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}
	public Boolean isOccupied()
	{
		return this.isOccupied;
	}

	public Boolean isRoomCenter()
	{
		return this.isRoomCenter;
	}

	public Boolean isLabel()
	{
		return this.isLabel;
	}

	public Boolean isDoorway()
	{
		return this.isDoorway;
	}
	public void setIsDoorway(Boolean isDoorway)
	{
		this.isDoorway = isDoorway;
	}

	public char getSecretPassage()
	{
		return this.secretPassage ;
	}
	
	public void setSecretPassage(char secretPassage)
	{
		this.secretPassage = secretPassage;
	}

	
	public char getInitial()
	{
		return this.initial;
	}

	public void setDoorDirection(DoorDirection doorDirection)
	{
		this.doorDirection = doorDirection;
	}
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
	}
	
}

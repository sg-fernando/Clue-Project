package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell
{

	private int row, col;
	
	private Boolean isRoom, isOccupied, isRoomCenter, isLabel, isDoorway;

	
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	private char secretPassage;
	
	public BoardCell(int row, int col)
	{
		this.row = row;
		this.col = col;
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
	
	public void setOccupied(boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}
	public Boolean getOccupied()
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

	public char getSecretPassage() {
		return this.secretPassage ;
	}

	public Object getDoorDirection()
	{
		return null;
	}
}

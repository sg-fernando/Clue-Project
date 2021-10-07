package clueGame;

import java.util.HashSet;
import java.util.Set;


public class BoardCell
{

private int row, col;
	
	private Boolean isRoom, isOccupied ;

	
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
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
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}
	public Boolean getOccupied()
	{
		return isOccupied;
	}

}

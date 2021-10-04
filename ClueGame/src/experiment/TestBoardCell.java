package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell
{
	private int row;
	private int column;
	
	private boolean room;
	private boolean occupied;
	
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.room = false;
		this.occupied = false;
	}
	
	public void addAdjacency(TestBoardCell cell)
	{
		adjList.add(cell);
	}
	public Set<TestBoardCell> getAdjList()
	{
		return adjList;
	}
	public void setIsRoom(boolean room)
	{
		this.room = room;
	}
	public boolean isRoom()
	{
		return room;
	}
	
	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
	public boolean getOccupied()
	{
		return occupied;
	}
}
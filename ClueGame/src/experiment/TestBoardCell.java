package experiment;

import java.util.Set;

public class TestBoardCell
{
	private int row;
	private int column;
	
	private boolean room;
	private boolean occupied;
	
	private Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int column)
	{
		this.row = row;
		this.column = column;
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
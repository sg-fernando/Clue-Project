package experiment;

import java.util.Set;

public class TestBoardCell
{
	private int row;
	private int column;
	
	private boolean room;
	private boolean occupied;
	
	private Set<TestBoardCell> adjList;
	
	TestBoardCell(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	void addAdjacency(TestBoardCell cell)
	{
		adjList.add(cell);
	}
	Set<TestBoardCell> getAdjList()
	{
		return adjList;
	}
	void setRoom(boolean room)
	{
		this.room = room;
	}
	boolean isRoom()
	{
		return room;
	}
	
	void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
	boolean getOccupied()
	{
		return occupied;
	}
}
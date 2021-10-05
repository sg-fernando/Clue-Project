package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell
{
	private int row, col;
	
	private Boolean isRoom, isOccupied ;

	
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col)
	{
		this.row = row;
		this.col = col;
		this.isRoom = false;
		this.isOccupied = false;
	}
	
	public void addAdjacency(TestBoardCell cell)
	{
		adjList.add(cell);
	}
	public Set<TestBoardCell> getAdjList()
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
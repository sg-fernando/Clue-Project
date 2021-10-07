package clueGame;

public class Room
{
	
	private String name;
	private BoardCell center;
	private BoardCell label;
	
	public Room(String n, BoardCell c, BoardCell l)
	{
		this.name = n;
		this.center = c;
		this.label = l;
	}
	
	public String getName()
	{
		return this.name;
	}

}

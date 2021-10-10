package clueGame;

public class Room
{
	
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}

	public BoardCell getLabelCell()
	{
		return this.labelCell;
	}
	public void setLabelCell(BoardCell labelCell)
	{
		this.labelCell = labelCell;
	}

	public BoardCell getCenterCell()
	{
		return this.centerCell;
	}
	public void setCenterCell(BoardCell centerCell)
	{
		this.centerCell = centerCell;
	}
}

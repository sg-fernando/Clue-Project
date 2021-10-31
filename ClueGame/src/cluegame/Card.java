package cluegame;

public class Card
{
	private CardType type;
	private String name;
	
	public Card(String typeStr, String name)
	{
		this.name = name;
		if (typeStr.equals("Room"))
		{
			this.type = CardType.ROOM;
		}
		else if (typeStr.equals("Player"))
		{
			this.type = CardType.PLAYER;
		}
		else
		{
			this.type = CardType.WEAPON;
		}
	}
	
	public Boolean equals(Card other)
	{
		return (other.name.equals(this.name) && other.type == this.type);
	}
	
	public CardType getType() { return type; }
	public String getName() { return name; }
}

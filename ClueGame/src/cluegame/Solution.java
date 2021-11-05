package cluegame;

public class Solution
{
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution(Card player, Card room, Card weapon)
	{
		this.person = player;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Boolean contains(Card card)
	{
		return person.equals(card) || room.equals(card) || weapon.equals(card);
	}
	
	public Card getPerson() { return person; }
	public Card getRoom() { return room; }
	public Card getWeapon() { return weapon; }
}

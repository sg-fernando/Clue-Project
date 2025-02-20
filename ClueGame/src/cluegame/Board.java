package cluegame;


import java.io.BufferedReader;
import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Board extends JPanel
{
	private static final long serialVersionUID = 1L;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private int numRows;
	private int numColumns;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<Card> deck;
	private Set<Player> players;
	
	private Player disproven;
	private Player currentPlayer;
	
	private Boolean unfinished = false;
	
	private Solution solution;
	
	private int roll;

	public static final String WALKWAY = "Walkway";
	public static final String UNUSED = "Unused";
	
	//singleton design pattern
	private static Board theInstance = new Board();
	
	private Board()
	{	
		super();
	}

	public static Board getInstance()
    {
		return theInstance;
    }
	
	public void initialize()
	{
		//initialize a board
		List<String[]> list = new ArrayList<>();
		try
		{
			//load setup and config files
			//make roomMap and load board data into list
			loadSetupConfig();
			list = loadLayoutConfig();
		} 
		catch (BadConfigFormatException e)
		{
			e.printStackTrace();
		}
		//build board using board data from list
    	buildBoard(list);
    	//build adjacency list for all cells
    	buildAdjLists();
    	
    	targets = new HashSet<>();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY.brighter().brighter().brighter());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int xOffset = 0;
		int yOffset = 0;
		int width = getWidth()/numColumns;
		int height = getHeight()/numRows;
		
		for (BoardCell[] row : grid)
		{
			for (BoardCell cell : row)
			{
				cell.draw(width, height, xOffset, yOffset, g, getRoom(cell));
				xOffset += width;
			}
			xOffset = 0;
			yOffset += height;
		}
		
		for (Player player : players)
		{
			player.draw(width, height, g);
		}
	}

	public Card handleSuggestion(Solution suggestion, Player player)
	{
		Card r = null;
		
		for (Player p : players)
		{
			if (p.disproveSuggestion(suggestion) != null && (player != p))
			{
				r = p.disproveSuggestion(suggestion);
				disproven = p;
			}
			if (Boolean.TRUE.equals(p.equals(suggestion.getPerson())))
			{
				p.newPosition(getCell(player.getRow(),player.getColumn()));
				p.setSuggest(true);
			}
		}
		return r;
	}
	
	public Player getDisprovenPlayer()
	{
		return disproven;
	}
	
	public Boolean checkAccusation(Card player, Card room, Card weapon)
	{
		return solution.getPerson().equals(player) && solution.getRoom().equals(room) && solution.getWeapon().equals(weapon);
	}
	
	public void deal()
	{
		Set<Card> tempDeck = new HashSet<>(deck);
		tempDeck = dealSolution(tempDeck);
		
		int rand;
		int count;
		for (Player player : players)
		{
			count = 0;
			rand = new Random().nextInt(tempDeck.size()-2);
			for (Card card : new HashSet<Card>(tempDeck))
			{
				if (count >= rand && count <= rand + 2)
				{
					if (Boolean.TRUE.equals(player.isHuman()))
					{
						player.updateHand(card);
					}
					else
					{
						((ComputerPlayer)player).updateHand(card);
					}
				}
				count++;
			}
			for (Card cardHand : player.getHand())
			{
				tempDeck.remove(cardHand);
			}
		}
	}
	
	private Set<Card> dealSolution(Set<Card> d)
	{
		int player = 0;
		int room = 0;
		int weapon = 0;
		int randP = new Random().nextInt(6);
		int randR = new Random().nextInt(9);
		int randW = new Random().nextInt(6);
		
		Card playerCard = null;
		Card roomCard = null;
		Card weaponCard = null;
	
		for (Card card : d)
		{
			if (card.getType() == CardType.PLAYER)
			{
				if (player == randP)
				{
					playerCard = card;
				}
				player++;
			}
			else if (card.getType() == CardType.ROOM)
			{
				if (room == randR)
				{
					roomCard = card;
				}
				room++;
			}
			else if (card.getType() == CardType.WEAPON)
			{
				if (weapon == randW)
				{
					weaponCard = card;
				}
				weapon++;
			}
		}
		
		System.out.println(playerCard.getName());
		System.out.println(roomCard.getName());
		System.out.println(weaponCard.getName());
		d.remove(playerCard);
		d.remove(roomCard);
		d.remove(weaponCard);
		
		solution = new Solution(playerCard, roomCard, weaponCard);
		return d;
	}
	
	private void buildBoard(List<String[]> list)
	{
		//initialize new grid
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				//make board cell from data
				BoardCell boardCell = new BoardCell(row, col, list.get(row)[col].charAt(0));
				
				//if the cell value is 2 char long then its a room center, label, or a door
				if (list.get(row)[col].length() > 1)
				{
					char secondChar = list.get(row)[col].charAt(1);
					Room room = getRoom(boardCell.getInitial());
					//check whether room center, label, or door (+ direction)
					switch (secondChar)
					{
					case '*':
						room.setCenterCell(boardCell);
						boardCell.setRoomCenter(true);
						break;
					case '#':
						room.setLabelCell(boardCell);
						boardCell.setLabel(true);
						break;
					case '<':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.LEFT);
						break;
					case '>':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.RIGHT);
						break;
					case '^':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						boardCell.setDoorway(true);
						boardCell.setDoorDirection(DoorDirection.DOWN);
						break;
					default:
						boardCell.setSecretPassage(secondChar);
						break;
					}
				}
				//add board cell to board
				grid[row][col] = boardCell;
			}
		}
	}
	
	private void buildAdjLists()
	{
		//initialize three board cell variables to use
		BoardCell currentCell;
		BoardCell centerCell;
		//iterate through grid
		for (int row = 0; row < numRows; row++)
		{
			for (int col = 0; col < numColumns; col++)
			{
				//set current cell to the cell at the current row and column
				currentCell = getCell(row,col);
	
				if (Boolean.TRUE.equals(currentCell.isSecretPassage()))
				{
					//get the center cell for the room to which the secret passage belongs
					centerCell = getRoom(currentCell).getCenterCell();
					//get the center cell for the room to which the secret passage leads
					BoardCell secretPassage = getRoom(currentCell.getSecretPassage()).getCenterCell();
					//add the destination of the passage to the center cell
					centerCell.addAdjacency(secretPassage);
				}
				if (getRoom(currentCell).getName().equals(WALKWAY))
				{
					//check up, down, left, right of current cell if not out of bounds
					if (row != 0 && getRoom(getCell(row-1,col)).getName().equals(WALKWAY))
					{
						//add [up] cell to current cell adjacency list
						currentCell.addAdjacency(getCell(row-1,col));
					}
					if (row != numRows-1 && getRoom(getCell(row+1,col)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row+1,col));
					}
					if (col != 0 && getRoom(getCell(row,col-1)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row,col-1));
					}
					if (col != numColumns-1 && getRoom(getCell(row,col+1)).getName().equals(WALKWAY))
					{
						currentCell.addAdjacency(getCell(row,col+1));
					}
					
					if (Boolean.TRUE.equals(currentCell.isDoorway()))
					{
						switch (getCell(row,col).getDoorDirection())
						{
						case UP:
							//get the center cell of the room [above] the current cell
							centerCell = getRoom(getCell(row-1,col)).getCenterCell();
							//add the center cell of the room to the adjacency list of the current cell
							currentCell.addAdjacency(centerCell);
							//ALSO add the current cell to the room's center cell adjacency list
							centerCell.addAdjacency(currentCell);
							break;
						case DOWN:
							centerCell = getRoom(getCell(row+1,col)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						case RIGHT:
							centerCell = getRoom(getCell(row,col+1)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						case LEFT:
							centerCell = getRoom(getCell(row,col-1)).getCenterCell();
							currentCell.addAdjacency(centerCell);
							centerCell.addAdjacency(currentCell);
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	
	public Set<BoardCell> calcTargets(BoardCell startCell, int pathlength)
	{
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		
		findAllTargets(startCell, pathlength);
		return targets;
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps)
	{
		for (BoardCell adjCell : thisCell.getAdjList())
		{
			if ( visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoomCenter()) )
			{
				continue;
			}
			
			visited.add(adjCell);
			
			if ( (numSteps == 1 && !adjCell.isOccupied()) || Boolean.TRUE.equals(adjCell.isRoomCenter()) )
			{
				targets.add(adjCell);
			}
			else
			{
				findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile)
	{
		this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}
	
	public void loadSetupConfig() throws BadConfigFormatException
	{
		roomMap = new HashMap<>();
		deck = new HashSet<>();
		players = new LinkedHashSet<>();
		try ( BufferedReader reader = new BufferedReader(new FileReader(this.setupConfigFile)) )
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				// a slash is a comment and should not be read in as data
				if (line.charAt(0) == '/')
				{
					continue;
				}
				String[] row = line.split(", ");
				//make a new room using the name given in the text file
				if (row[0].equals("Room") || row[0].equals("Space"))
				{
					Room room = new Room(row[1]);
					//add to the roomMap with the char that represents the room as the key
					roomMap.put(row[2].charAt(0), room);
				}
				else if (row[0].equals("Player"))
				{
					Player player;
					String[] position = row[4].split(",");
					int rowPos = Integer.parseInt(position[0]);
					int colPos = Integer.parseInt(position[1]);
					if (row[3].equals("Human"))
					{
						player = new HumanPlayer(row[1], row[2].charAt(0),rowPos,colPos);
						currentPlayer = player;
					}
					else
					{
						player = new ComputerPlayer(row[1], row[2].charAt(0),rowPos,colPos);
					}
					players.add(player);
				}
				if (!row[0].equals("Space"))
				{
					Card card = new Card(row[0], row[1]);
					deck.add(card);
				}
				
			}
			for (Player player : players)
			{
				for (Card card : deck)
				{
					if (Boolean.FALSE.equals(player.isHuman()))
					{
						((ComputerPlayer)player).updateUnseen(card);
					}
				}
			}
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
	}
	
	public List<String[]> loadLayoutConfig() throws BadConfigFormatException
	{
		List<String[]> list = new ArrayList<>();
		try ( BufferedReader reader = new BufferedReader(new FileReader(this.layoutConfigFile)) )
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				//get a row from the csv file and add it to the array list
				String[] row = line.split(",");
				list.add(row);
			}
			
			//set the rows and columns based on the retrieved data
			this.numRows = list.size();
			this.numColumns = list.get(0).length;
		}
		catch (IOException e)
		{
			throw new BadConfigFormatException();
		}
		return list;
	}
	
	public Room getRoom(char c) { return roomMap.get(c); }
	
	public Room getRoom(BoardCell cell)
	{
		char c = cell.getInitial();
		return getRoom(c);
	}
	
	public Set<BoardCell> getTargets() { return targets; }
	
	public int getNumRows() { return this.numRows; }
	
	public int getNumColumns() { return this.numColumns; }
	
	public BoardCell getCell(int row, int col) { return grid[row][col]; }
	
	public Set<BoardCell> getAdjList(int row, int col) { return getCell(row,col).getAdjList(); }

	public Boolean isUnfinishedTurn() { return unfinished; }
	public void setUnfinished(Boolean unfinished)
	{
		this.unfinished = unfinished;
	}
	
	public int getRoll() { return roll; }
	public void roll()
	{
		roll = new Random().nextInt(6);
		roll++;
	}
	
	public Player getCurrentPlayer() { return currentPlayer; }
	public void updateCurrentPlayer()
	{
		Boolean setNext = false;
		for (Player player : players)
		{
			if (Boolean.TRUE.equals(setNext))
			{
				currentPlayer = player;
				setNext = false;
				break;
			}
			if (Boolean.TRUE.equals(player.equals(currentPlayer)))
			{
				setNext = true;
			}
		}
		if (Boolean.TRUE.equals(setNext))
		{
			Iterator<Player> itr = players.iterator();
			currentPlayer = itr.next();
		}
	}
	
	public Set<Card> getDeck() { return deck; }
	public Solution getSolution() { return solution; }
	public Set<Player> getPlayers() { return players; }
	public HumanPlayer getHuman()
	{
		for (Player player : players)
		{
			if (Boolean.TRUE.equals(player.isHuman()))
			{
				return (HumanPlayer) player;
			}
		}
		return null;
	}
	
	public void clearTargets()
	{
		targets.clear();
	}
	
	public Boolean isTarget(BoardCell cell) {
		for (BoardCell target : targets)
		{
			if (Boolean.TRUE.equals(target.equals(cell)))
			{
				return true;
			}
		}
		return false;
	}
	// testing; set up known solution values for comparison
	public void setRoll(int roll)
	{
		this.roll = roll;
	}
	public void setSolution(Solution sol) {
		this.solution = sol;
	}
	public void setPlayers(HashSet<Player> p) {
		this.players = p;
	}
	
	public Boolean checkAccusation(Solution S) {
		return this.getSolution().equals(S);
	}

}

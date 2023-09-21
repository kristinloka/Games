package inf101.sem2.game;

import java.util.ArrayList;



import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.player.Player;

/**
 * This class implements the game Connect Four.
 * Connect Four is a 2 player game where the goal is to get four in a row.
 * The game is normally played on a vertical frame such that 
 * pieces fall down due to gravity and the players sit on opposite sides of the board.
 * 
 * The game board is a grid with 6 rows and 7 columns.
 * 
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class ConnectFour extends Game {

	/**
	 * Constructs a ConnectFour game with 2 players where p1 starts the game.
	 * 
	 * @param graphics - The graphics used to display the game for the human players
	 * @param p1 - First player
	 * @param p2 - Second player
	 */
	public ConnectFour(Graphics graphics, Player p1, Player p2) {
		this(graphics);
		players.add(p1);
		players.add(p2);
	}

	public ConnectFour(Graphics graphics) {
		super(new GameBoard(6, 7), graphics);
	}

	public ConnectFour(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(6, 7), graphics, players);
	}

	@Override
	public boolean isWinner(Player p) {
		return board.countNumInRow(p) >= 4;
		
	}

	@Override
	public boolean gameOver() {
		for(Player p : players) {
			if(isWinner(p)) {
				return true;
			}
		}

		return board.isFull();
	}

	@Override
	public void makeMove(Location loc) {
		super.makeMove(drop(loc));
	}

	/**
	 * This method moves a location downwards as far as possible.
	 * This because the game rules states that the lower rows needs
	 * to be occupied first. In physical version this is enforced by gravity.
	 * @param loc
	 * @return
	 */
	private Location drop(Location loc) {
		while(board.canPlace(below(loc))) {
			loc = below(loc);
		}
		return loc;
	}
	
	private Location below(Location loc) {
		return loc.getNeighbor(GridDirection.SOUTH);
	}
	
	/**
	 * This is a method that given a column finds the unique place in that
	 * column where it is possible to place.
	 * 
	 * If the column given is full the Location returned will be outside the valid grid.
	 * 
	 * @param col the column to place in.
	 * @return the Location in that column where one can place.
	 */
	public Location drop(int col) {
		return drop(new Location(0, col));
	}

	@Override
	public boolean canPlace(Location loc) {
		return board.canPlace(drop(loc));
	}

	@Override
	public Game copy() {
		ConnectFour game = new ConnectFour(graphics);
		copyTo(game);
		return game;
	}

	@Override
	public String getName() {
		return "Connect Four";
	}
	
	@Override
	public List<Location> getPossibleMoves() {
		ArrayList<Location> goodMoves = new ArrayList<Location>();
		for(Location loc : super.getPossibleMoves()) {
			if(drop(loc).equals(loc))
				goodMoves.add(loc);
		}
		return goodMoves;
	}
}

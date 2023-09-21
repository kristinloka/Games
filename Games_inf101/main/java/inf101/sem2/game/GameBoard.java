package inf101.sem2.game;

import inf101.grid.Grid;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.player.Player;

public class GameBoard extends Grid<Player> {

	public GameBoard(int rows, int cols) {
		super(rows, cols);
	}

	@Override
	public void set(Location loc, Player elem) {
		if(canPlace(loc)) {
			super.set(loc, elem);
		} else {
			System.err.println("Can not place at " + loc + ".");
		}
	}
	
	public void setForce(Location loc, Player elem) {
		super.set(loc, elem);
	}



	/**
	 * This method determines whether or not it is allowed to place on a given location.
	 * This method does not consider the rules of the game just whether the space on the gameBoard
	 * is available.
	 * <p>
	 * This implementation only allows for one piece in each square of the board.
	 *
	 * @return If the given location is empty
	 */
	public boolean canPlace(Location loc) {

		try {
			return get(loc) == null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		String s = "";

		for(int row = 0; row < numRows(); row++) {
			//print row
			for(int col = 0; col < numColumns(); col++) {
				Player p = get(new Location(row, col));
				if(p == null) {
					s += ' ';
				} else {
					s += p.getSymbol();
				}
				if(col < numColumns() - 1) {
					s += '|';
				}
			}
			//print newline
			s += "\n";

			//print horizontal separator
			if(row < numRows() - 1) {
				for(int col = 0; col < numColumns(); col++) {
					s += '-';
					if(col < numColumns() - 1) {
						s += '+';
					}
				}
				s += "\n";
			}

		}

		return s;
	}

	/**
	 * @return The maximum number of pieces in a row that the given player has.
	 */
	public int countNumInRow(Player p) {

		int max = 0;
		for(Location loc : locations()) {
			for(GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
				max = Math.max(max, countNumInRow(p, loc, dir));
			}
		}

		return max;

	}

	private int countNumInRow(Player p, Location start, GridDirection dir) {
		int count = 0;

		while(isOnGrid(start) && get(start) == p) {
			count++;
			start = start.getNeighbor(dir);
		}

		return count;
	}
	
	/**
	 * @param p current player
	 * @return The number of pieces player p has on the board
	 */
	public int countNumOfPieces(Player p) {
		int num = 0;
		for(Location loc : locations()) {
			if(get(loc) == p) {
				num++;
			}
		}
		return num;
	}
	
	public int totalNumOfPieces() {
		int num = 0;
		for(Location loc : locations()) {
			if(!canPlace(loc)) {
				num++;
			}
		}
		return num;
	}
	

	/**
	 * This method checks whether all locations of the board is occupied
	 * or there are any empty places on the board.
	 *
	 * @return If the board is full
	 */
	public boolean isFull() {
		for(Location loc : locations()) {
			if(canPlace(loc)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Makes a shallow copy of the board
	 */
	@Override
	public GameBoard copy() {
		GameBoard board = new GameBoard(this.numRows(), this.numColumns());
		fillCopy(board);
		return board;
	}
}

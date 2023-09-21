package inf101.sem2.game;

import java.util.ArrayList;


import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.player.Player;


public class Othello extends Game {

	public Othello(Graphics graphics, Player p1, Player p2) {
		this(graphics);
		players.add(p1);
		players.add(p2);
		
		board.set(new Location(3,3), p2);
		board.set(new Location(3,4), p1);
		board.set(new Location(4,3), p1);
		board.set(new Location(4,4), p2);
		
	}

	public Othello(Graphics graphics) {
		super(new GameBoard(8, 8), graphics);
	}
	
	public Othello(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(8, 8), graphics, players);	
			
		initBoard();		
	}

	/**
	 * This method place the four start pieces on the board.
	 */
	private void initBoard() {		
		board.set(new Location(3,4), players.getCurrentPlayer());
		board.set(new Location(4,3), players.getCurrentPlayer());
		players.nextPlayer();
		board.set(new Location(3,3), players.getCurrentPlayer());
		board.set(new Location(4,4), players.getCurrentPlayer());
		players.nextPlayer();
	}

	@Override
	public Game copy() {
		Othello game = new Othello(graphics);
		copyTo(game);
		return game;
	}
	
	@Override
	public void restart() {
		super.restart();
		initBoard();
	}

	@Override
	public boolean isWinner(Player player) {	
		if(getPossibleMoves().isEmpty()) {
			players.nextPlayer();
			if(getPossibleMoves().isEmpty()) {
				return board.countNumOfPieces(player) > (board.totalNumOfPieces()-board.countNumOfPieces(player));
			}
		}
		return false;
	}
	
	@Override
	public boolean gameOver() {
		for(Player p : players) {
			if(isWinner(p) || isTie(p)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "Othello";
	}
	

	@Override
	public boolean canPlace(Location loc) {
		if (!super.canPlace(loc)) {
			return false;
		}
		Location nextLoc;
		for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
			nextLoc = loc.getNeighbor(dir);
			if (!board.isOnGrid(nextLoc)) {
				continue;
			}
			Player neighbor = board.get(nextLoc);
			if (neighbor != null && !neighbor.equals(getCurrentPlayer())) {
				Location currentLoc = nextLoc;
				while (board.isOnGrid(currentLoc) && board.get(currentLoc) != null
						&& !board.get(currentLoc).equals(getCurrentPlayer())) {
					currentLoc = currentLoc.getNeighbor(dir);

				}
				if (board.isOnGrid(currentLoc) && board.get(currentLoc) != null) {
					return true;
				}
			}
		}
		return false;
	}



	/*
	 * Legger til brikker som tilhører motstanderen i en retning i en liste og flipper dem
	 */
	/**
	 * This method finds and flips the opponents pieces when the current player place on a valid location according to the rules.
	 * @param loc where to place
	 */
	public void flip(Location loc) {
		if (canPlace(loc)) {
			board.set(loc, getCurrentPlayer());
			Location nextLoc;
			ArrayList<Location> toFlip = new ArrayList<>();
			for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
				toFlip.clear();
				nextLoc = loc.getNeighbor(dir);
				if (!board.isOnGrid(nextLoc)) {
					continue;
				}
				Player neighbor = board.get(nextLoc);
				if (neighbor != null && !neighbor.equals(getCurrentPlayer())) {
					Location currentLoc = nextLoc;
					while (board.isOnGrid(currentLoc) && board.get(currentLoc) != null
							&& !board.get(currentLoc).equals(getCurrentPlayer())) {
						
						toFlip.add(currentLoc);
						currentLoc = currentLoc.getNeighbor(dir);
					}
					if (board.isOnGrid(currentLoc) && board.get(currentLoc) != null) {
						for (Location location : toFlip) {
							swap(location, getCurrentPlayer());
						}
					}
				}
			}
		}
	}
	

	/**
	 * This method change the current symbol on a location to the current player's symbol. 
	 * @param loc where to place
	 * @param p current player
	 */
	private void swap(Location loc, Player p) {
		board.setForce(loc, getCurrentPlayer());
	}

	@Override
	public void makeMove(Location loc) {
		if (!canPlace(loc)) {
			throw new IllegalArgumentException("Can not make that move");
		}
		flip(loc);
		players.nextPlayer();
		if (getPossibleMoves().isEmpty()) {
			players.nextPlayer();
		}		
	}
	
}

package inf101.sem2.game;

import java.util.ArrayList;

import java.util.List;

import inf101.grid.Location;
import inf101.sem2.player.Player;
import inf101.sem2.player.PlayerList;

/**
 * This class models turn based games where each round the current player gets to place one piece.
 * Games of this sort has win/tie/loose conditions and rules for where it is possible to place pieces.
 * <p>
 * This type of games does not allow players to move pieces unless it is a valid move.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public abstract class Game {

	//keeps track of where players have placed their pieces
	protected GameBoard board;

	//displays messages and current state of the game
	protected Graphics graphics;

	//keeps track of whose turn it is
	protected PlayerList players;

	/* ************** Constructors ****************/
	public Game(GameBoard board, Graphics graphics) {
		this.board = board;
		this.graphics = graphics;
		players = new PlayerList();
	}

	public Game(GameBoard board, Graphics graphics, Iterable<Player> players) {
		this(board, graphics);
		for(Player p : players) {
			addPlayer(p);
		}
	}

	/* **************   Methods   *****************/

	/**
	 * This is the main game loop making sure each player gets to place one piece each turn.
	 */
	public void run() {

		//game loop
		while(!gameOver()) {
			Location loc = getCurrentPlayer().getMove(copy());
			if(canPlace(loc)) {
				makeMove(loc);
				graphics.displayMessage(getCurrentPlayer() + "'s turn!");
			} else {
				System.err.println("Invalid move by player " + getCurrentPlayer() + " lost turn.");
			}
		}

		//print results when game is over
		graphics.displayMessage("Game is over!");
		graphics.display(board);

		for(Player p : players) {
			if(isWinner(p)) {
				graphics.displayMessage("Player " + p + " has won!");
			}
			if(isTie(p)) {
				graphics.displayMessage("It's a tie!");
			}
		}

	}

	/**
	 * When players are asked to make a move we don't want them to change the
	 * state of the game so we send them a copy of the game.
	 */
	public abstract Game copy();

	/**
	 * This method fills in a game with the state of a given game.
	 * The method is there so the implementation of the copy method is simplified in the subclasses.
	 * @param target
	 */
	protected void copyTo(Game target) {
		target.board = board.copy();
		target.graphics = graphics;
		target.players = players.copy();
	}

	/**
	 * This method performs a move for the current player and advances to next player
	 *
	 * @param loc
	 */
	public void makeMove(Location loc) {
		if(!canPlace(loc)) {
			throw new IllegalArgumentException("Can not make that move");
		}

		board.set(loc, getCurrentPlayer());
		players.nextPlayer();
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param player
	 */
	void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Gets a copy of the GameBoard.
	 */
	public GameBoard getGameBoard() {
		return board.copy();
	}

	/**
	 * The game has rules for where the players can place.
	 * This method checks if the current player can place on a given location.
	 * <p>
	 * This is both used to verify that the move current player returns is valid
	 *
	 * @param loc    - where to place
	 * @return true if it is a valid move, false otherwise.
	 */	
	public boolean canPlace(Location loc) {
		return board.canPlace(loc);
	}

	/**
	 * Checks if the given player has reached the winning condition of the game.
	 * 
	 * @param player
	 * @return
	 */
	public abstract boolean isWinner(Player player);

	/**
	 * This method checks if the given player is a looser.
	 * In two player games there is normally one winner and one looser or it is a draw.
	 * But in games with more players this might be different.
	 * 
	 * @param player
	 * @return
	 */
	public boolean isLooser(Player player) {
		for(Player p : players()) {
			if(p != player && isWinner(p)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks if the game is a tie.
	 * @param player current player
	 * @return true if it is a tie, false if not.
	 */
	protected boolean isTie(Player player) {
		if(getPossibleMoves().isEmpty()) {
			players.nextPlayer();
			if(getPossibleMoves().isEmpty()) {
				return board.countNumOfPieces(player) == (board.totalNumOfPieces()-board.countNumOfPieces(player));
			}		
		}
		return false;
	}

	/**
	 * Computes a score for the player p in the current game
	 * to be used when choosing the best move.
	 *
	 * @param game
	 * @param p
	 * @return
	 */
	public int score(Player p) {
		if(isWinner(p)) {
			return 1;
		}
		if(isLooser(p)) {
			return -1;
		}
		return 0;
	}

	public abstract boolean gameOver();

	public void displayBoard() {
		graphics.display(board);
	}

	public Iterable<Player> players() {
		return players.copy();
	}

	public Player getCurrentPlayer() {
		return players.getCurrentPlayer();
	}

	public List<Location> getPossibleMoves() {
		ArrayList<Location> moves = new ArrayList<>();
		for(Location loc : board.locations()) {
			if(canPlace(loc)) {
				moves.add(loc);
			}
		}
		return moves;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public abstract String getName();

	public void restart() {
		board.clear();
		players.restart();
		graphics.display(board);
	}
}

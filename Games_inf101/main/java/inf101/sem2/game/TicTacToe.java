package inf101.sem2.game;

import inf101.sem2.player.Player;

/**
 * Tic-tac-toe is a 2 player game played on a 3 by 3 grid where 
 * each player places a piece on a selected location.
 * The goal is for one player to get 3 pieces in a row.
 * 
 * 3 in a row can be horizontaly, vertical or diagonally.
 * 
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class TicTacToe extends Game {

	public TicTacToe(Graphics graphics) {
		super(new GameBoard(3, 3), graphics);
	}

	public TicTacToe(Graphics graphics, Player player1, Player player2) {
		super(new GameBoard(3, 3), graphics);
		addPlayer(player1);
		addPlayer(player2);	
	}


	public TicTacToe(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(3, 3), graphics, players);
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
	public TicTacToe copy() {
		TicTacToe game = new TicTacToe(graphics);
		copyTo(game);
		return game;
	}

	@Override
	public boolean isWinner(Player p) {
		return board.countNumInRow(p) >= 3;
	}

	@Override
	public String getName() {
		return "Tic Tac Toe";
	}
	
	
}

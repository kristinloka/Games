package inf101.sem2.player;

import inf101.grid.Location;
import inf101.sem2.game.Game;

/**
 * This AI is based on an algorithm that is not curriculum for INF101, but maybe for INF102.
 * Since many of the students had tried implementing such an AI I have included one in this solution.
 * But such a level of AI is not expected at the level of INF101.
 * <p>
 * This player will work both for TicTacToe and for FourInARow
 * And also for other 2 player games.
 * <p>
 * This player uses a minimax strategy to search for the best move.
 * That means that it tries all possible moves and gives a score to each move.
 * The score is given by calling the method recursively for the other player to
 * make the move that is best for him.
 * We assume that what is best for you is worst for your opponent,
 * otherwise this strategy will not work.
 * <p>
 * In this case we give score 1 for win, score 0 for draw and score -1 for loosing.
 * Note that the sum of the scores for both players always is 0.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class MiniMaxPlayer extends AbstractPlayer {

	int depth; //defines how many steps ahead the search should continue;

	public MiniMaxPlayer(char piece, int level) {
		super(piece, "MiniMax");
		depth = level;
	}

	@Override
	public Location getMove(Game game) {
		Strategy best = bestMove(game, depth);
		return best.move;
	}

	/**
	 * Chooses the move that maximizes the players score
	 *
	 * @param game
	 * @param depth
	 * @return
	 */
	private Strategy bestMove(Game game, int depth) {
		Strategy best = null;
		int bestScore = 0;

		//try each possible strategy
		for(Location loc : game.getPossibleMoves()) {

			//make a copy of the game and try the move
			Game newGame = game.copy();
			newGame.makeMove(loc); //note that this changes the current player in the copy but not the real game

			Strategy current = null;
			if(newGame.gameOver() || depth == 1) { //No more moves can be made
				current = new Strategy(loc, newGame);
			} else {
				//call recursively such that the opponent makes the move that is best for her/him
				//change the sign since this is the score of the best move for opponent
				current = bestMove(newGame, depth - 1);
				current.move = loc;
			}

			int score = current.game.score(game.getCurrentPlayer());
			//keep the best Strategy
			if(best == null || score > bestScore) {
				best = current;
				bestScore = score;
			}
		}
		if(best == null) {
			System.err.println("This should not happen! No moves possible?");
		}
		return best;

	}
}

/**
 * A inner class only to be used by MiniMax player
 * This class keeps track of a move and a score associated with that move
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
class Strategy {
	Location move;
	Game game;

	public Strategy(Location move, Game game) {
		this.move = move;
		this.game = game;
	}
}




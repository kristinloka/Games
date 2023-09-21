package inf101.sem2.player;

import javax.swing.JOptionPane;

import inf101.grid.Location;
import inf101.sem2.GUI.GameGUI;
import inf101.sem2.game.Game;


/**
 * This Player should be used if one wants input from GUI.
 * The game loop will stop when reaching an instance of GuiPlayer
 * and when a mouse click is detected the game loop will resume.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class GuiPlayer extends AbstractPlayer {

	Location nextMove;

	public GuiPlayer(char piece, String name) {
		super(piece, name);
	}

	public GuiPlayer(char piece) {
		super(piece, readPlayerName(piece));
	}

	@Override
	public Location getMove(Game game) {
		GameGUI gui;
		try {
			gui = (GameGUI) game.getGraphics();
		} catch (Exception e1) {
			throw new IllegalArgumentException("GuiPlayer can not play without a GUI");
		}

		game.displayBoard();

		while(true) {
			nextMove = gui.getMove();
			if(hasValidMove(game)) {
				//System.err.println("Gui player moves " + nextMove);
				return nextMove;
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Sleep interrupted");
				}
			}

			if(gui.wantRestart) {
				throw new RestartException();
			}

			if(gui.ended) {
				throw new GameEndedException();
			}
		}
	}

	private boolean hasValidMove(Game game) {
		if(nextMove == null) {
			return false;
		}
		return game.canPlace(nextMove);
	}

	/**
	 * Asks player to type in name in a GUI pop up
	 *
	 * @param symbol - The symbol representing this player
	 * @return the name chosen by the player
	 */
	public static String readPlayerName(char symbol) {
		String name = null;
		while(!AbstractPlayer.isValidName(name)) {
			name = JOptionPane.showInputDialog("Player " + symbol + ". Type in your name.");
		}
		return name;
	}
}

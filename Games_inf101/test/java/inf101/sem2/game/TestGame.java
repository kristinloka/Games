package inf101.sem2.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import inf101.GetStarted;
import inf101.grid.Location;
import inf101.sem2.player.DumbPlayer;
import inf101.sem2.player.Player;
import inf101.sem2.terminal.TerminalGraphics;
import org.junit.jupiter.api.Test;

class TestGame {
	
	
	@BeforeEach
	void testReadConditions() {
		assertTrue(GetStarted.hasRead);
	}
	
	Player p1;
	Player p2;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new DumbPlayer('X');
		p2 = new DumbPlayer('O');
	}


	@Test
	void testDumbPlayerCanPlay() {
		Game game = new TicTacToe(new TerminalGraphics(), p1, p2);
		game.run();
		assertTrue(game.gameOver());

		game = new ConnectFour(new TerminalGraphics(), p1, p2);
		game.run();
		assertTrue(game.gameOver());
		
		game = new Othello(new TerminalGraphics(), p1, p2);
		game.run();
		assertTrue(game.gameOver());
	}

}

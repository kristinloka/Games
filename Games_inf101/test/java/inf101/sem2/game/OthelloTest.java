package inf101.sem2.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf101.GetStarted;
import inf101.grid.Location;
import inf101.sem2.player.DumbPlayer;
import inf101.sem2.player.Player;
import inf101.sem2.terminal.TerminalGraphics;

class OthelloTest {

	@BeforeEach
	void testReadConditions() { 
		assertTrue(GetStarted.hasRead);
	}
	
	Othello game;
	Player p1;
	Player p2;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new DumbPlayer('X');
		p2 = new DumbPlayer('O');
		game = new Othello(new TerminalGraphics(), p1, p2);
	}
	
	@Test
	void testCanPlace() {
		//Check if you can place next to start piece
		assertTrue(game.canPlace(new Location(5,4)));
			
		game.board.setForce(new Location(3,2), p1);
		game.board.setForce(new Location(3,1), p2);
		assertTrue(game.canPlace(new Location(3,0)));
		assertFalse(game.canPlace(new Location(7,5)));
		
		//Check that you can´t place in a filled location
		assertFalse(game.canPlace(new Location(3,2)));
			
		//Check if next player also can place their piece
		game.players.nextPlayer();
		game.board.setForce(new Location(7,0), p2);
		game.board.setForce(new Location(6,0), p1);
		assertTrue(game.canPlace(new Location(5,0)));
		assertFalse(game.canPlace(new Location(4,6)));	
	}
	
	@Test
	void testCanPlaceOutOfBoard() {
		assertFalse(game.canPlace(new Location(8,8)));
		assertFalse(game.canPlace(new Location(-1,-1)));
	}
	
	@Test
	void testFlip() {	
		game.board.setForce(new Location(0,0), p1);
		game.board.setForce(new Location(0,1), p2);
		game.flip(new Location(0,2));
		assertEquals(p1, game.board.get(new Location(0,1)));
		
		game.players.nextPlayer();
		game.board.setForce(new Location(6,0), p2);
		game.board.setForce(new Location(6,1), p1);
		game.board.setForce(new Location(6,2), p1);
		game.flip(new Location(6,3));
		assertEquals(p2, game.board.get(new Location(6,1)));
		assertEquals(p2, game.board.get(new Location(6,2)));
		
	}
	
	@Test
	void testIsTie(){
		for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = 0; row < game.board.numRows()/2; row++) {
                game.board.setForce(new Location(row, column), p1);
            }
        }
        for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = game.board.numRows()/2; row < game.board.numRows(); row++) {
                game.board.setForce(new Location(row, column), p2);
            } 
        }
        assertTrue(game.isTie(p1));
       	
	}
	
	@Test
	void testIsWinner() {
		for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = 0; row < game.board.numRows()-2; row++) {
                game.board.setForce(new Location(row, column), p1);
            }
        }
        for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = game.board.numRows()-2; row < game.board.numRows(); row++) {
                game.board.setForce(new Location(row, column), p2);
            } 
        }
        assertFalse(game.isWinner(p2));
        assertTrue(game.isWinner(p1));
	}
	
	@Test
	void testIsLooser() {
		for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = 0; row < game.board.numRows()-2; row++) {
                game.board.setForce(new Location(row, column), p1);
            }
        }
        for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = game.board.numRows()-2; row < game.board.numRows(); row++) {
                game.board.setForce(new Location(row, column), p2);
            } 
        }
        assertFalse(game.isLooser(p1));
        assertTrue(game.isLooser(p2));
	}	
	
	
	@Test
	void testGameOverWinner() {
		for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = 0; row < game.board.numRows()-2; row++) {
                game.board.setForce(new Location(row, column), p1);
            }
        }
        for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = game.board.numRows()-2; row < game.board.numRows(); row++) {
                game.board.setForce(new Location(row, column), p2);
            } 
        }
        assertTrue(game.gameOver());		
	}
	
	@Test 
	void testGameOverTie() {
		for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = 0; row < game.board.numRows()/2; row++) {
                game.board.setForce(new Location(row, column), p1);
            }
        }
        for (int column = 0; column < game.board.numColumns(); column++) {
            for (int row = game.board.numRows()/2; row < game.board.numRows(); row++) {
                game.board.setForce(new Location(row, column), p2);
            } 
        }
        assertTrue(game.gameOver());
	}
	
	@Test
	void testNotGameOver() {
		game.board.setForce(new Location(3,2), p1);
		game.board.setForce(new Location(3,1), p2);
		assertFalse(game.gameOver());
	}

}

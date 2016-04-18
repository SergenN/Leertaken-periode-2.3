package com.github.leertaken.leertaak3.opdracht7.test;

import com.github.leertaken.leertaak3.opdracht7.TicTacToe;
import junit.framework.TestCase;
import org.junit.Test;

public class TestGame extends TestCase {

    public TestGame(String arg0) {
        super(arg0);
    }

    @Test
    public void testWin(){
        TicTacToe game = new TicTacToe();

        game.setHumanPlays();
        game.playMove(0);

        game.setHumanPlays();
        game.playMove(3);

        game.setHumanPlays();
        game.playMove(6);

        assertTrue(game.gameOver());
        assertEquals(game.winner(), "human wins");
    }

    @Test
    public void testPositionValue(){
        TicTacToe game = new TicTacToe();
        assertTrue(game.positionValue() == TicTacToe.UNCLEAR);

        game.setHumanPlays();
        game.playMove(0);
        assertTrue(game.positionValue() == TicTacToe.UNCLEAR);

        game.setHumanPlays();
        game.playMove(3);
        assertTrue(game.positionValue() == TicTacToe.UNCLEAR);

        game.setHumanPlays();
        game.playMove(6);
        assertTrue(game.positionValue() == TicTacToe.HUMAN_WIN);
    }

    public void testChooseMove(){
        TicTacToe game = new TicTacToe();
        game.setHumanPlays();
        game.playMove(0);

        game.setComputerPlays();
        game.playMove(game.chooseMove());

        game.setHumanPlays();
        game.playMove(3);

        game.setComputerPlays();
        int move = game.chooseMove();
        assertEquals(move, 6);//Assert it's gonna block me
        game.playMove(move);

        game.setHumanPlays();
        game.playMove(5);

        game.setComputerPlays();
        game.playMove(game.chooseMove());

        game.setHumanPlays();
        game.playMove(2);

        game.setComputerPlays();
        move = game.chooseMove();
        assertEquals(move, 7);
        game.playMove(move);

        System.out.println(game.toString());
        assertTrue(game.gameOver());
    }
}

package com.github.leertaken.leertaak3.opdracht7;

import java.util.*;
/*
 * Textual User Interface
 */
class Tui {
    private Scanner reader = new Scanner(System.in);
    private TicTacToe game;

    public Tui() {
        do {
            System.out.println("*** new Game ***\n");
            game = new TicTacToe();
            if (game.computerPlays()){
                System.out.println("I start:\n");
            }
            else{
                System.out.println("You start:\n");
            }
            while (!game.gameOver()) {
                game.playMove(move());
                System.out.println(game);
            }
            System.out.println("Game over, " + game.winner());
        } while (nextGame());
    }

    public static void main(String[] args) {
        new Tui();
    }

    private int move() {
        String humanMove;

        if (game.computerPlays()) {
            int compMove = game.chooseMove();
            System.out.println("Computer Move = " + compMove);
            return compMove;
        } else {
            do {
                System.out.print("Human move = ");
                humanMove = reader.next();
            }
            while (!game.moveOk(humanMove));
            return Integer.parseInt(humanMove);
        }
    }

    private boolean nextGame() {
        Character answer;
        do {
            System.out.print("next Game? enter Y/N: ");
            answer = (reader.next()).charAt(0);
            System.out.println("" + answer);
        }
        while (!(answer == 'Y' || answer == 'y' || answer == 'N' || answer == 'n'));
        return answer == 'Y' || answer == 'y';
    }
}


// enter integer for the position on the tictactoe board
// 012
// 345
// 678
package com.github.leertaken.leertaak3.opdracht7;

import java.util.Random;

public class TicTacToe {
	private static final int HUMAN = 0;
	private static final int COMPUTER = 1;
	public static final int EMPTY = 2;

	public static final int HUMAN_WIN = 0;
	public static final int DRAW = 1;
	public static final int UNCLEAR = 2;
	public static final int COMPUTER_WIN = 3;

	private int[][] board = new int[3][3];
	private Random random = new Random();
	private int side = random.nextInt(2);
	private int position = UNCLEAR;
	private char computerChar, humanChar;

	// Constructor
	public TicTacToe() {
		clearBoard();
		initSide();
	}

	private void initSide() {
		if (side == COMPUTER) {
			computerChar = 'X';
			humanChar = 'Ø';
		} else {
			computerChar = 'Ø';
			humanChar = 'X';
		}
	}

	public void setComputerPlays() {
		side = COMPUTER;
		initSide();
	}

	public void setHumanPlays() {
		side = HUMAN;
		initSide();
	}

	public boolean computerPlays() {
		return side == COMPUTER;
	}

	public int chooseMove() {
		Best best = chooseMove(COMPUTER);
		return best.row * 3 + best.column;
	}

	// Find optimal move
	private Best chooseMove(int side) {
		int opponent;         // The other side
		Best reply;           // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		int bestRow = 0;
		int bestColumn = 0;
		int value;

		if ((simpleEval = positionValue()) != UNCLEAR) {
			return new Best(simpleEval);
		}

		//Normally opponent would always be human but this is for Junit Testing
		if (side == COMPUTER){
			opponent = HUMAN;
			value = HUMAN_WIN;
		} else {
			opponent = COMPUTER;
			value = COMPUTER_WIN;
		}

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (squareIsEmpty(row, column)) {
					place(row, column, side);//Do PC move
					reply = chooseMove(opponent);//Predict Opponent Move
					place(row, column, EMPTY);//Undo PC move

					if (side == COMPUTER && reply.value > value || side == HUMAN && reply.value < value){//Checks for highest valued move The brain behind the operations
						value = reply.value;
						bestRow = row;
						bestColumn = column;
					}
				}
			}
		}
		return new Best(value, bestRow, bestColumn);
	}


	/**
	 * Checks if a move is ok.
	 * @param move - the String input of a user
	 * @return true if the move is ok.
     */
	public boolean moveOk(String move) {
		int location;
		try {
			location = Integer.parseInt(move);
		}catch (NumberFormatException e){
			return false;
		}
		return (location >= 0 && location <= 8 && board[location / 3][location % 3] == EMPTY);
	}

	// play move
	public void playMove(int move) {
		board[move / 3][move % 3] = side;
		if (side == COMPUTER){
			side = HUMAN;
		} else {
			side = COMPUTER;
		}
	}


	// Simple supporting routines
	private void clearBoard() {
		for (int x = 0; x < board.length; x++){
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = EMPTY;
			}
		}
	}


	private boolean boardIsFull() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] == EMPTY){
					return false;
				}
			}
		}
		return true;
	}

	// Returns whether 'side' has won in this position
	private boolean isAWin(int side) {
		//diagonal
		int diagonalCounter = 0;
		for(int i = 0; i < board.length; i++){
			if (board[i][board.length-1-i] == side) diagonalCounter ++;
			if (diagonalCounter == board.length){
				return true;
			}
		}

		//reverse diagonal
		diagonalCounter = 0;
		for(int i = 0; i < board.length; i++){
			if (board[i][i] == side) diagonalCounter ++;
			if (diagonalCounter == board.length){
				return true;
			}
		}

		//horizontal & vertical
		for (int x = 0; x < board.length; x++) {
			int horizontalCounter = 0;
			int verticalCounter = 0;
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] == side) verticalCounter++;
				if (board[y][x] == side) horizontalCounter++;
				if (verticalCounter == board.length || horizontalCounter == board.length){
					return true;
				}
			}
		}
		return false;
	}

	// Play a move, possibly clearing a square
	private void place(int row, int column, int piece) {
		board[row][column] = piece;
	}

	private boolean squareIsEmpty(int row, int column) {
		return board[row][column] == EMPTY;
	}

	// Compute static value of current position (win, draw, etc.)
	public int positionValue() {
		if (isAWin(COMPUTER)){
			return COMPUTER_WIN;
		}
		if (isAWin(HUMAN)){
			return HUMAN_WIN;
		}
		if (boardIsFull()){
			return DRAW;
		}
		return UNCLEAR;
	}


	public String toString() {
		StringBuilder field = new StringBuilder();
		int slot = 0;
		for (int x = 0; x < board.length; x++){
			for (int y = 0; y < board[x].length; y++){
				if (board[x][y] == COMPUTER){
					field.append(computerChar);
				} else
				if (board[x][y] == HUMAN){
					field.append(humanChar);
				} else
				if (board[x][y] == EMPTY){
					field.append(slot);
				}
				slot++;
			}
			field.append("\n\r");
		}
		return field.toString();
	}

	public boolean gameOver() {
		position = positionValue();
		return position != UNCLEAR;
	}

	public String winner() {
		if (position == COMPUTER_WIN){
			return "computer wins";
		}
		else if (position == HUMAN_WIN){
			return "human wins";
		}
		else return "it's a draw";
	}
}
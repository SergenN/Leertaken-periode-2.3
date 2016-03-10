package com.github.leertaken.leertaak3.opdracht2;
import java.util.Stack;
/** the solution is a sequence of cards placed on the board according to the card positions
    example without border
*/
public class Solution extends Stack<Candidate> {
	// The board is an 2D array.
	// 0123
	// 0..-.
	// 1---.
	// 2.---
	// 3..-.
	private Candidate[][] board = new Candidate[4][4];

	// card positions on the board
	// the first card position on the board are
	// {0,2}, {1,0}. {1,1}
	private int[] row = {0, 1, 1, 1, 2, 2, 2, 3};
	private int[] column = {2, 0, 1, 2, 1, 2, 3, 2};
	//  indices of adjacent cards in the solution.
	//                0   1  2   3   4    5     6    7
	int[][] check = {{}, {}, {1}, {0}, {2}, {3, 4}, {5, 6}, {7}};

	public Solution() {

	}

	// Checks whether a candidate with card CardChar is in
	// an adjacent position of the board position (row, column)
	// @param row, column, candidate
	// @return Boolean indicating if cardChar is found.
	// can be used in the methods fits and isCorrect
	private boolean bordersCard(int row, int column, char cardChar) {
        boolean match = false;
		//boven
		if (row-1 >= 0 && !match) {
			if (board[(row - 1)][column] != null) {
				match = board[row - 1][column].getCardChar() == cardChar;
			}
		}
		//onder
		if (row+1 <= 3 && !match) {
			if (board[(row + 1)][column] != null) {
				match = board[row + 1][column].getCardChar() == cardChar;
			}
		}

		//links
		if(column-1 >= 0 && !match) {
			if (board[row][column - 1] != null) {
				match = board[row][(column - 1)].getCardChar() == cardChar;
			}
		}

		//rechts
		if (column+1 <= 3 && !match) {
			if (board[row][column + 1] != null) {
                match = board[row][(column + 1)].getCardChar() == cardChar;
			}
		}
		return match;
	}


	/**
	 * Checks whether candidate card of same kind.
	 * Checks whether by placing candidate the solution sofar still complies with the rules
	 *
	 * @param candidate
	 * @return boolean indicating whether this candidate can be put in the
	 * next free position.
	 */
	public boolean fits(Candidate candidate) {
		return !bordersCard(row[size()], column[size()], candidate.getCardChar());
	}

	public void record(Candidate candidate) {
		int i = this.size(); // i= index in this stack of next for the next candidate
		board[row[i]][column[i]] = candidate; //x=row, y=column
		this.push(candidate);
	}

	public boolean complete() {
		return this.size() == 8 && isCorrect();
	}

	public void show() {
		System.out.println(this);
	}

	public Candidate eraseRecording() {
		int i = this.size() - 1;           // i= index of the candidate that is removed from this Stack;
		board[row[i]][column[i]] = null;  // remove candidate from board
		return this.pop();
	}

	// can be used in method isCorrect
	private char mustBeAdjacentTo(char card) {
		if (card == 'A') return 'K';
		if (card == 'K') return 'Q';
		if (card == 'Q') return 'J';
		return '?'; //error
	}

	/**
	 * Checks whether the rules below are fulfilled
	 * For the positions that can be checked for solution sofar.
	 * Rules:
	 * Elke aas (ace) grenst (horizontaal of verticaal) aan een heer (king).
	 * Elke heer grenst aan een vrouw (queen).
	 * Elke vrouw grenst aan een boer (jack).
	 *
	 * @return true if all checks are correct.
	 */
	// uses methods borderCard and mustBeAdjacent to
	private boolean isCorrect() {
        for (int i = 0; i < column.length; i++) {
            char hasToBorder = mustBeAdjacentTo(board[row[i]][column[i]].getCardChar());
            if (hasToBorder == '?') continue;
            if (!bordersCard(row[i], column[i], hasToBorder)) {
                return false;
            }
        }
        return true;
	}


	/**
	 * @return a representation of the solution on the board
	 */
	public String toString() {
        String builder = "";
        for (Candidate[] YRow : board) {
            for (Candidate candidate : YRow) {
                if (candidate != null) {
                    builder += (candidate.getCardChar() + " ");
                } else {
                    builder += "  ";
                }
            }
            builder += ("\n\r");
        }
        return builder;
    }
}

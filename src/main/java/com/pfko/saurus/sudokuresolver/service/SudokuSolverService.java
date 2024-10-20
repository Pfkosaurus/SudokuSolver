package com.pfko.saurus.sudokuresolver.service;


import org.springframework.stereotype.Service;
import com.pfko.saurus.sudokuresolver.model.SudokuBoard;

@Service
public class SudokuSolverService {

	public boolean solve(SudokuBoard board) {
	    int[][] grid = board.getBoard();

	    //Go trough all cells and wi find all empty (0) fields
	    for (int row = 0; row < 9; row++) {
	        for (int col = 0; col < 9; col++) {
	            if (grid[row][col] == 0) { // if field is empty then
	                // we will empty field with valid number
	                for (int num = 1; num <= 9; num++) {
	                    if (isSafe(grid, row, col, num)) {
	                        grid[row][col] = num; // fill number
	                        
	                        // Recursive call for next cell
	                        if (solve(board)) {
	                            return true; // success
	                        }
	                        grid[row][col] = 0; //if not success, we canncel the number 
	                    }
	                }
	                return false; // No number can fit there
	            }
	        }
	    }
	    return true; // all cells are filled
	}

  private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // look for empty (0) cell
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        // try fill number there (1-9)
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num; // Pridáme číslo

                            // recurzive call  for next cell
                            if (solveSudoku(board)) {
                                return true; // success
                            }

                            // if not success, we cancel the number 
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true; // Sudoku solved
    }

  /*
   * Control, if number is valid to fill in cell
   * */
    public boolean isSafe(int[][] board, int row, int col, int num) {
        // Line control
        for (int x = 0; x < 9; x++) {
            if (board[row][x] == num) {
                return false;
            }
        }

        // Column control
        for (int x = 0; x < 9; x++) {
            if (board[x][col] == num) {
                return false;
            }
        }

        // control 3x3 matrix
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
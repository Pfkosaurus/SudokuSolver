package com.pfko.saurus.sudokuresolver.controller;

import com.pfko.saurus.sudokuresolver.model.SudokuBoard;
import com.pfko.saurus.sudokuresolver.controller.SudokuGenerator;
import com.pfko.saurus.sudokuresolver.service.SudokuSolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class SudokuController {
    private final SudokuGenerator sudokuGenerator;
    private final SudokuSolverService sudokuSolverService;

    private SudokuBoard currentBoard;

    @Autowired
    public SudokuController(SudokuGenerator sudokuGenerator, SudokuSolverService sudokuSolverService) {
        this.sudokuGenerator = sudokuGenerator;
        this.sudokuSolverService = sudokuSolverService;
        this.currentBoard = new SudokuBoard();
    }

    @GetMapping("/")
    public String getSudoku(Model model) {
        currentBoard.setBoard(sudokuGenerator.generateEmptyBoard());
        model.addAttribute("board", currentBoard.getBoard());
        return "sudoku";
    }

    @PostMapping("/solve")
    public String solveSudoku(String[] boardInput, Model model) {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String value = boardInput[i * 9 + j];
                    int intValue = (value == null || value.isEmpty()) ? 0 : Integer.parseInt(value);
                    currentBoard.getBoard()[i][j] = intValue;
                }
            }

            // Validácia board pred riešením
            if (!validateCurrentBoard(currentBoard.getBoard())) {
                model.addAttribute("error", "The input numbers are not in order according to Sudoku rules.\r\n"
                		+ " (a number can only be repeated once in one 9x9 matrix)\r\n"
                		+ "(a number can only be repeated once in the entire line)\r\n"
                		+ "(a number can only be repeated once in the entire column)");
                model.addAttribute("board", currentBoard.getBoard());
                return "sudoku";
            }

            boolean solved = sudokuSolverService.solve(currentBoard);
            model.addAttribute("board", currentBoard.getBoard());
            model.addAttribute("solved", solved);
        } catch (Exception e) {
            model.addAttribute("error", "A error occured while processing Sudoku: " + e.getMessage());
            model.addAttribute("board", currentBoard.getBoard());
        }

        return "sudoku";
    }

    /*
     * A method to regenerate a blank Sudoku while preserving the existing numbers
     */
    @PostMapping("/regenerate")
    public String regenerateSudoku(Model model) {
        // Empty grid setting - all fields set to 0
        int[][] emptyBoard = new int[9][9]; // Create emtpy grind
        currentBoard.setBoard(emptyBoard); // Set an empty grid on the current board
        
        // We update the model to display empty fields in the template
        model.addAttribute("board", currentBoard.getBoard());
        
        return "sudoku"; // return call to sudoku page
    }

    // Validation of existing cells in matrix
    private boolean validateCurrentBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                if (num != 0) { // Consider only non-empty values
                    // Temporary remove number for validation check
                    board[i][j] = 0; 
                    // If number is not valid for sudoku rules, return false
                    if (!sudokuSolverService.isSafe(board, i, j, num)) {
                        return false; // number is not valid
                    }
                    board[i][j] = num; // restore number
                }
            }
        }
        return true; // all number are valid for sudoku rules
    }
}
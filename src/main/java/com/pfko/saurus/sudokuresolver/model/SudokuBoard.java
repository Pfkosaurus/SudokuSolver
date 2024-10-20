package com.pfko.saurus.sudokuresolver.model;

public class SudokuBoard {
    private int[][] board;

    public SudokuBoard() {
        board = new int[9][9]; // 9x9 matrix
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
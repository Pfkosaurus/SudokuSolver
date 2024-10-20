package com.pfko.saurus.sudokuresolver.controller;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SudokuGenerator {
    private final Random random = new Random();

    public int[][] generateEmptyBoard() {
        return new int[9][9]; // return empty matrix 9x9
    }
}

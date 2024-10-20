Sudoku Solver Application
Overview
This project is a web-based Sudoku Solver built using Java and Spring Boot. The application allows users to input their own Sudoku puzzle, validate the input for correctness, and solve the puzzle automatically. It also includes a feature to regenerate an empty Sudoku board, allowing users to enter a new puzzle.
This project is one day challenge just for fun, because what you can do with free time during vacation? :D

Features
Sudoku Puzzle Input: Users can enter their own Sudoku puzzle with partial numbers.
Puzzle Validation: The application checks if the user input follows the Sudoku rules.
Solve Function: Automatically fills the missing numbers according to Sudoku rules using backtracking algorithm.
Regenerate Function: Clears the board so users can input a new puzzle.
Responsive UI: A clean and user-friendly interface that works on both desktop and mobile devices.

How to run:
in mvn console, write
	mvn clean install
run application
	mvn spring-boot:run
Open browser, and navigate to 
	http://localhost:8080

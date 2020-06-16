package sudokulogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Backtracking implements SudokuSolver {

    private static final int empty = 0;
    private static final int size = 9;

    private List<Integer> candidates = new ArrayList<>();

    /**
     * Backtracking constructor.
     * Fills list with numbers as candidates to solve.
     */
    public Backtracking() {
        //Random rand = new Random();
        int number = 1;
        while (candidates.size() < size) {

            if (!candidates.contains(number)) {
                candidates.add(number++);
            }
        }
    }

    /**
     * Recursive algorithm for solving sudoku board.
     *
     * @param sudokuBoard - Sudoku board to be solved
     * @return - returns boolean value (true - solved, false - in progress)
     */
    public boolean solve(SudokuBoard sudokuBoard) {

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                if (sudokuBoard.getField(row, col).getValue() == empty) {

                    for (int numPosition = 0; numPosition < size; numPosition++) {

                        //shuffled and got number (1 to size)
                        int number = shuffled(numPosition);

                        if (sudokuBoard.sudokuCheck(row, col, number)) {

                            sudokuBoard.getField(row, col).setValue(number);
                            if (solve(sudokuBoard)) {
                                return true;
                            } else {
                                sudokuBoard.getField(row, col).setValue(empty);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private int shuffled(int pos) {

        Collections.shuffle(candidates);
        return candidates.get(pos);
    }
}

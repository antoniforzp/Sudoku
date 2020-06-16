package sudokucomponents;

import org.junit.jupiter.api.Test;
import sudokulogic.Backtracking;
import sudokulogic.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

class SudokuComponentsTest {

    private final int size = 9;
    private SudokuBoard boardTest = generate();

    private SudokuBoard generate() {
        SudokuBoard board = new SudokuBoard();
        Backtracking backSolver = new Backtracking();

        backSolver.solve(board);
        return board;
    }

    @Test
    void rowCheck() {
        for (int i = 0; i < size; i++) {
            for (int number = 1; number <= size; number++) {
                assertFalse(this.boardTest.getRow(i).verify(number));
            }
        }
    }

    @Test
    void hashCode1() {
        SudokuBoard boardTest2 = generate();
        assertTrue(boardTest.hashCode() != boardTest2.hashCode());
    }

    @Test
    void columnCheck() {
        for (int i = 0; i < size; i++) {
            for (int number = 1; number <= size; number++) {
                assertFalse(this.boardTest.getColumn(i).verify(number));
            }
        }
    }

    @Test
    void boxCheck() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int number = 1; number <= size; number++) {
                    assertFalse(this.boardTest.getBox(i, j).verify(number));
                }
            }
        }
    }

    //coś tu nie bangla
    @Test
    void cloneCheck() throws CloneNotSupportedException {
        for (int i = 0; i < size; i++) {

            SudokuBoard board = new SudokuBoard();
            Backtracking backSolver = new Backtracking();
            backSolver.solve(board);

            SudokuRow rowOriginal = board.getRow(i);
            SudokuRow rowClone = (SudokuRow) board.getRow(i).clone();

            assertEquals(rowOriginal, rowClone);
            assertNotSame(rowOriginal, rowClone);

            SudokuColumn colOriginal = boardTest.getColumn(i);
            SudokuColumn colClone = (SudokuColumn) boardTest.getColumn(i).clone();

            assertEquals(colOriginal, colClone);
            assertNotSame(colOriginal, colClone);

            for (int j = 0; j < size; j++) {

                SudokuBox boxOriginal = boardTest.getBox(i, j);
                SudokuBox boxClone = (SudokuBox) boardTest.getBox(i, j).clone();

                assertEquals(boxOriginal, boxClone);
                assertNotSame(boxOriginal, boxClone);
            }
        }
    }
}
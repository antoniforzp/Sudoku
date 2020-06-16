package sudokulogic;

import org.junit.jupiter.api.Test;
import sudokucomponents.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard boardTest = generate();

    private SudokuBoard generate() {
        SudokuBoard board = new SudokuBoard();
        Backtracking backSolver = new Backtracking();

        backSolver.solve(board);

        return board;
    }

    @Test
    void hashCode1() {
        boolean res;
        SudokuBoard boardTest2 = generate();
        res = boardTest.hashCode() != boardTest2.hashCode();
        assertTrue(res);
    }
    
    @Test
    void cloneCheck() throws CloneNotSupportedException {
        SudokuBoard boardClone = boardTest.clone();
        assertEquals(boardClone, boardTest);
        assertNotSame(boardClone, boardTest);
    }
}
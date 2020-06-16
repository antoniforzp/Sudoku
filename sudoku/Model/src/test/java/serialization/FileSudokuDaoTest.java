package serialization;

import exceptions.ApplicationException;
import org.junit.jupiter.api.Test;
import sudokulogic.Backtracking;
import sudokulogic.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuDaoTest {


    private SudokuBoard boardTest = generate();

    SudokuBoard generate() {

        SudokuBoard board = new SudokuBoard();
        Backtracking solver = new Backtracking();

        solver.solve(board);
        return board;
    }

    @Test
    void testWriteRead() {
        String filename = "src/test/testFiles/boardTest.txt";

        //writing SudokuBoard to file
        try {
            FileSudokuDao daoTool = new FileSudokuDao();
            daoTool.write(boardTest, filename);

            System.out.println("board written to: " + filename);
            System.out.println(boardTest.toString());

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        //reading SudokuBoard from file
        SudokuBoard newBoard = new SudokuBoard();
        try {
            FileSudokuDao daoTool = new FileSudokuDao();
            newBoard = daoTool.read(filename);

            System.out.println("board read from: " + filename);
            System.out.println(newBoard.toString());

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        assertEquals(boardTest, newBoard);
        assertNotSame(boardTest, newBoard);
    }
}
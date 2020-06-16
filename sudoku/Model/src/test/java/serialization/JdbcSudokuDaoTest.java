package serialization;

import exceptions.ApplicationException;
import exceptions.DaoException;
import org.junit.jupiter.api.Test;
import sudokulogic.Backtracking;
import sudokulogic.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuDaoTest {

    @Test
    void testWriteRead() throws ApplicationException {
        JdbcSudokuDao jdbcSudokuDao = new JdbcSudokuDao();

        SudokuBoard board = new SudokuBoard();
        Backtracking solver = new Backtracking();
        solver.solve(board);

        System.out.println(board.toString());

        jdbcSudokuDao.write(board, "boardTest");

        SudokuBoard board2 = jdbcSudokuDao.read("boardTest");
        System.out.println(board2.toString());

        assertEquals(board, board2);
        assertNotSame(board, board2);

        jdbcSudokuDao.delete("boardTest");
        jdbcSudokuDao.close();
    }
}
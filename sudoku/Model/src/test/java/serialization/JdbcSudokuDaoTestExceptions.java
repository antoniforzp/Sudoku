package serialization;

import exceptions.DaoException;
import org.junit.jupiter.api.Test;
import sudokulogic.SudokuBoard;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuDaoTestExceptions {

    @Test
    void jdbcReadNotExistingBoard() {
        assertThrows(DaoException.class, () -> {
            JdbcSudokuDao daoTool = new JdbcSudokuDao();
            SudokuBoard testBoard = daoTool.read("notExistingBoard");
        });
    }

    @Test
    void jdbcWriteNullBoard() {
        assertThrows(DaoException.class, () -> {
            JdbcSudokuDao daoTool = new JdbcSudokuDao();
            daoTool.write(null, "testBoard");
            daoTool.delete("testBoard");
        });
    }

    @Test
    void jdbcWriteExistingBoard() {
        assertThrows(DaoException.class, () -> {
            JdbcSudokuDao daoTool = new JdbcSudokuDao();
            daoTool.write(new SudokuBoard(), "testBoard");
            //trying to write sudoku board twice with the same name
            daoTool.write(new SudokuBoard(), "testBoard");
            daoTool.delete("testBoard");
        });
    }

    @Test
    void jdbcDeleteNotExistingBoard() {
        assertThrows(DaoException.class, () -> {
            JdbcSudokuDao daoTool = new JdbcSudokuDao();
            daoTool.write(new SudokuBoard(), "testBoard");
            daoTool.delete("testBoard");
            //trying to delete sudoku board twice with the same name
            daoTool.delete("testBoard");
        });
    }

    @Test
    void jdbcDeleteNull(){
        assertThrows(DaoException.class, () -> {
            JdbcSudokuDao daoTool = new JdbcSudokuDao();
            daoTool.write(new SudokuBoard(), "testBoard");
            daoTool.delete("testBoard");
            //trying to delete sudoku board twice with the same name
            daoTool.delete(null);
        });
    }
}
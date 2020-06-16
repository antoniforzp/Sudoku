package serialization;

import exceptions.ApplicationException;
import exceptions.DaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuDaoTestExceptions {

    @Test
    void testNullBoard() {
        try (FileSudokuDao dao = new FileSudokuDao()) {
            assertThrows(DaoException.class, () -> dao.write(null, "src/test/testFiles/boardTest.txt"));
        } catch (ApplicationException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void testReadNotFile() {
        try (FileSudokuDao dao = new FileSudokuDao()) {
            assertThrows(DaoException.class,() -> dao.read("notExistingFile"));
        } catch (ApplicationException ex) {
            ex.printStackTrace();
        }
    }

    //When file is not existing newFile() creates new one with the name passed to constructor
    /*@Test
    void testWriteToNonExistingFile() {
        try (FileSudokuDao dao = new FileSudokuDao("nonExistingFile"))
        {
            SudokuBoard sudokuBoard = new SudokuBoard();
            assertThrows(DaoException.class, () -> dao.write(sudokuBoard));
        } catch (ApplicationException ex) {
            ex.printStackTrace();
        }
    }*/
}
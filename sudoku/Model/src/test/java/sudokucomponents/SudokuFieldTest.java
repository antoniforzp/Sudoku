package sudokucomponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void equals1() {
        SudokuField field = new SudokuField();
        SudokuField field1 = new SudokuField();

        field.setValue(0);
        field1.setValue(0);

        assertEquals(field, field1);

        field1.setValue(1);
        assertNotEquals(field, field1);
    }

    @Test
    void clone1() throws CloneNotSupportedException {
        SudokuField field = new SudokuField();
        SudokuField field1 = field.clone();

        assertEquals(field, field1);
        assertNotSame(field, field1);
    }
}
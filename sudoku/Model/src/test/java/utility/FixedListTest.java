package utility;

import org.junit.jupiter.api.Test;
import sudokucomponents.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

class FixedListTest {

    private SudokuField field = new SudokuField();

    @Test
    void testExceptions() {

        int size = 9;
        FixedList<SudokuField> fieldsArray = new FixedList<>(size);
        fieldsArray.set(0, new SudokuField());

        assertThrows(UnsupportedOperationException.class, fieldsArray::clear);
        assertThrows(UnsupportedOperationException.class, () -> fieldsArray.add(field));
        assertThrows(UnsupportedOperationException.class, () -> fieldsArray.add(0, field));
        assertThrows(UnsupportedOperationException.class, () -> fieldsArray.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> fieldsArray.remove(field));
        assertThrows(UnsupportedOperationException.class, () -> fieldsArray.removeRange(0, 1));
    }
}
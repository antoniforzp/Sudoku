package sudokucomponents;

import utility.FixedList;

public class SudokuRow extends BoardComponent {

    public SudokuRow(FixedList<SudokuField> fields) {
        this.fieldsArray = fields;
    }
}

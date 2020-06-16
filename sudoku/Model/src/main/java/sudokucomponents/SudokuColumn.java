package sudokucomponents;

import utility.FixedList;

public class SudokuColumn extends BoardComponent {

    public SudokuColumn(FixedList<SudokuField> fields) {
        this.fieldsArray = fields;
    }
}
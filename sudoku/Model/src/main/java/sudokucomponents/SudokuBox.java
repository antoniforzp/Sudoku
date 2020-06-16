package sudokucomponents;

import utility.FixedList;

public class SudokuBox extends BoardComponent {

    public SudokuBox(FixedList<SudokuField> fields) {
        this.fieldsArray = fields;
    }
}
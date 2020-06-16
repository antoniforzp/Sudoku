package sudokucomponents;

import java.io.Serializable;
import java.util.Objects;

// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------

public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
    private int value;

    public SudokuField() {
        value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    //====OVERRIDE===============================================================================

    @Override
    public String toString() {
        return value + " ";
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        SudokuField field = (SudokuField) obj;
        return this.value == field.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(final SudokuField o) {
        return Integer.compare(value, o.value);
    }

    @Override
    public final SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
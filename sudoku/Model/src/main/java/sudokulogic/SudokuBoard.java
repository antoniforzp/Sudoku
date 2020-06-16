package sudokulogic;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Objects;

import org.apache.commons.lang.builder.EqualsBuilder;

import sudokucomponents.SudokuBox;
import sudokucomponents.SudokuColumn;
import sudokucomponents.SudokuField;
import sudokucomponents.SudokuRow;
import utility.FixedList;

public class SudokuBoard implements Serializable, Cloneable {

    public final int size = 9;
    private FixedList<FixedList<SudokuField>> fields = new FixedList<>(size);

    //====CONSTRUCTOR============================================================================

    /**
     * Sudoku board constructor.
     * Initializes 2d fixedArrayList with new SudokuFields.
     */
    public SudokuBoard() {

        //fill arrayList with pointers to other sub arrayLists
        for (int i = 0; i < size; i++) {
            fields.set(i, new FixedList<>(size));
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fields.get(i).set(j, new SudokuField());
            }
        }
    }

    //====GETTERS and SETTERS COMPONENTS=========================================================

    /**
     * Returns selected SudokuField from fields array.
     *
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @return SudokuField object.
     */
    public SudokuField getField(int x, int y) {
        return fields.get(x).get(y);
    }

    /**
     * Returns selected SudokuRow object.
     *
     * @param col - column index(coordinate).
     * @return SudokuRow object.
     */
    public SudokuRow getRow(int col) {

        FixedList<SudokuField> fieldsArray = new FixedList<>(size);

        for (int i = 0; i < size; i++) {
            SudokuField field = new SudokuField();

            field.setValue(this.fields.get(i).get(col).getValue());
            fieldsArray.set(i, field);
        }
        return new SudokuRow(fieldsArray);
    }

    /**
     * Returns selected SudokuColumn object.
     *
     * @param row - row index(coordinate).
     * @return SudokuColumn object.
     */
    public SudokuColumn getColumn(int row) {
        FixedList<SudokuField> fieldsArray = new FixedList<>(size);

        for (int j = 0; j < size; j++) {
            SudokuField field = new SudokuField();

            field.setValue(this.fields.get(row).get(j).getValue());
            fieldsArray.set(j, field);
        }
        return new SudokuColumn(fieldsArray);
    }

    /**
     * Returns selected SudokuBox.
     *
     * @param row - row index(coordinate).
     * @param col - column index(coordinate).
     * @return SudokuBox object.
     */
    public SudokuBox getBox(int row, int col) {
        FixedList<SudokuField> fieldsArray = new FixedList<>(size);

        int counter = 0;
        int x = row - (row % 3);
        int y = col - (col % 3);

        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {

                SudokuField field = new SudokuField();
                field.setValue(this.fields.get(i).get(j).getValue());

                fieldsArray.set(counter, field);
                counter++;
            }
        }
        return new SudokuBox(fieldsArray);
    }

    //====CHECKING===============================================================================

    boolean sudokuCheck(int row, int col, int number) {
        return getRow(col).verify(number)
                && getColumn(row).verify(number)
                && getBox(row, col).verify(number);
    }

    //====OVERRIDE===============================================================================

    @Override
    public String toString() {

        ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
        toStringHelper.addValue("\n");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                toStringHelper.addValue(fields.get(i).get(j).getValue());
            }
            toStringHelper.addValue("\n");
        }
        return toStringHelper.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        SudokuBoard board = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(this.size, board.size)
                .append(this.fields, board.fields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields, size);
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clone = (SudokuBoard) super.clone();

        byte[] clonedObject;
        try {
            ByteArrayOutputStream byteOutArray = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOutArray);

            objectOut.writeObject(this);
            clonedObject = byteOutArray.toByteArray();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }

        try {
            ByteArrayInputStream byteInArray = new ByteArrayInputStream(clonedObject);
            ObjectInputStream objectIn = new ObjectInputStream(byteInArray);

            clone = (SudokuBoard) objectIn.readObject();
            return clone;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
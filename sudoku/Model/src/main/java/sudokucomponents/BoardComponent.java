package sudokucomponents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import utility.FixedList;

// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------

class BoardComponent implements Serializable, Cloneable {

    private static final int size = 9;
    FixedList<SudokuField> fieldsArray;

    BoardComponent() {
        this.fieldsArray = new FixedList<>(size);
    }

    public boolean verify(int number) {

        for (int i = 0; i < size; i++) {

            if (fieldsArray.get(i).getValue() == 0) {
                continue;
            }

            if (fieldsArray.get(i).getValue() == number) {
                return false;
            }
        }
        return true;
    }

    //====OVERRIDE===============================================================================

    @Override
    public String toString() {

        ToStringBuilder str = new ToStringBuilder(this);
        for (SudokuField sudokuField : fieldsArray) {
            str.append(sudokuField).append(" ");
        }
        return str.toString();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BoardComponent rhs = (BoardComponent) obj;
        return new EqualsBuilder()
                .append(fieldsArray, rhs.fieldsArray)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldsArray);
    }

    @Override
    public final BoardComponent clone() throws CloneNotSupportedException {
        BoardComponent clone = (BoardComponent) super.clone();

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

            clone = (BoardComponent) objectIn.readObject();
            return clone;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

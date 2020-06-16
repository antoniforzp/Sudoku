package serialization;

import exceptions.ApplicationException;
import exceptions.DaoException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.String;

import sudokulogic.SudokuBoard;

//EXCERCISE 12/13 PARTS ----------

public class FileSudokuDao extends DaoExtension<SudokuBoard> {

    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutput;

    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;

    /**
     * Dao tool constructor.
     *
     * @throws ApplicationException - throws custom ApplicationException when filename is null.
     */
    public FileSudokuDao() throws ApplicationException {
        super();
    }

    @Override
    public void close() throws DaoException {
        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
                objectOutput.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (IOException e) {
            throw new DaoException(DaoException.error_close, e);
        }
    }

    @Override
    public SudokuBoard read(String filename) throws DaoException {
        if (filename == null) {
            throw new DaoException(DaoException.null_file);
        }

        try {
            fileInputStream = new FileInputStream(filename);
            objectInputStream = new ObjectInputStream(fileInputStream);

            SudokuBoard board = (SudokuBoard) objectInputStream.readObject();
            close();

            return board;
        } catch (IOException e) {
            throw new DaoException(DaoException.null_file, e);
        } catch (ClassNotFoundException e) {
            throw new DaoException(DaoException.invalid_cast, e);
        }
    }

    @Override
    public void write(SudokuBoard obj, String filename) throws DaoException {
        if (filename == null) {
            throw new DaoException(DaoException.null_file);
        }

        if (obj == null) {
            throw new DaoException(DaoException.null_board);
        }

        File file = new File(filename);

        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutput = new ObjectOutputStream(fileOutputStream);

            objectOutput.writeObject(obj);
            close();

        } catch (IOException e) {
//            throw new DaoException(DaoException.error_open, e);
            e.printStackTrace();
        }
    }
}
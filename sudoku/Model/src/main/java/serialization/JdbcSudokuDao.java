package serialization;

import exceptions.ApplicationException;
import exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import sudokulogic.SudokuBoard;

//EXCERCISE 12/13 PARTS ----------
//EXCERCISE 12/13 PARTS ----------
//EXCERCISE 12/13 PARTS ----------

/*Questions:

- What is JDBC database driver?
A JDBC driver is a software component enabling a Java application to interact with a database.

- What is the purpose of Class.forName() method?
Returns the Class object associated with the class or interface with the given string name,
using the given class loader. The specified class loader is used to load the class or interface.

- What is ACID?
ACID (Atomicity, Consistency, Isolation, Durability) is a set of properties
of database transactions intended to guarantee validity even in the event of errors, power failures, etc.

- What is a transaction?
It is a logical unit that is independently executed for data retrieval or updates.

 */

public class JdbcSudokuDao extends DaoExtension<SudokuBoard> {

    //location of database configure file
    private static final String DB_URL = "jdbc:sqlite:SudokuBoards.db";

    //database utilities
    private static final String USER = "username";
    private static final String PASS = "password";

    //queries
    private static final String SQL_CREATE_BOARDS = "CREATE TABLE IF NOT EXISTS BOARDS ("
            + "[name] VARCHAR(255) PRIMARY KEY,"
            + "[creationDate] VARCHAR(255)"
            + ")";
    private static final String SQL_CREATE_FIELDS = "CREATE TABLE IF NOT EXISTS FIELDS ("
            + "[boardName] VARCHAR(255),"
            + "[x] INTEGER,"
            + "[y] INTEGER,"
            + "[value] INTEGER,"
            + "FOREIGN KEY(boardName) REFERENCES BOARDS(name),"
            + "CONSTRAINT field_primKey PRIMARY KEY (boardName, x, y)"
            + ")";

    private static final String SQL_READ_FIELDS = "SELECT * FROM FIELDS WHERE [boardName]=? AND [x]=? AND [y]=?";

    private static final String SQL_WRITE_BOARD = "INSERT INTO BOARDS([name], [creationDate]) VALUES(?, strftime('%d/%m/%Y %H:%M', 'now', 'localtime'))";
    private static final String SQL_WRITE_FIELD = "INSERT INTO FIELDS([boardName], [x], [y], [value]) VALUES(?, ?, ?, ?)";

    private static final String SQL_DELETE_BOARD = "DELETE FROM BOARDS WHERE [name]=?";
    private static final String SQL_DELETE_FIELDS = "DELETE FROM FIELDS WHERE [boardName]=?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM BOARDS";

    private Connection connection;

    //static statement cannot have throw signature
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            try {
                throw new DaoException(DaoException.error_sql);
            } catch (DaoException ex) {
                ex.printStackTrace();
            }
        }
    }

    public JdbcSudokuDao() throws ApplicationException {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            statement.execute(SQL_CREATE_BOARDS);
            statement = connection.createStatement();
            statement.execute(SQL_CREATE_FIELDS);

        } catch (SQLException se) {
            throw new DaoException(DaoException.error_sql);
        }
    }

    //<name, creationDate>
    public Map<String, String> selectAll() throws DaoException {
        Map<String, String> boards = new HashMap<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                boards.put(resultSet.getString("name"), resultSet.getString("creationDate"));
            }

        } catch (SQLException se) {
            throw new DaoException(DaoException.error_sql);
        }
        return boards;
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(DaoException.error_sql, e);
        }
    }

    @Override
    public SudokuBoard read(final String boardName) throws DaoException {
        if (boardName == null) {
            throw new DaoException(DaoException.null_board);
        }
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);

            final SudokuBoard sudokuBoard = new SudokuBoard();
            ResultSet resultSet;
            for (int x = 0; x < sudokuBoard.size; x++) {
                for (int y = 0; y < sudokuBoard.size; y++) {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_READ_FIELDS);

                    preparedStatement.setString(1, boardName);
                    preparedStatement.setInt(2, x);
                    preparedStatement.setInt(3, y);

                    resultSet = preparedStatement.executeQuery();
                    sudokuBoard.getField(x, y).setValue(resultSet.getInt(4));
                    resultSet.close();
                }
            }

            return sudokuBoard;

        } catch (SQLException e) {
            throw new DaoException(DaoException.error_sql, e);
        } catch (NullPointerException e) {
            throw new DaoException(DaoException.null_record, e);
        }
    }

    @Override
    public void write(SudokuBoard sudokuBoard, final String boardName) throws DaoException {
        if (sudokuBoard == null || boardName == null) {
            throw new DaoException(DaoException.null_board);
        }
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_WRITE_BOARD);
            preparedStatement.setString(1, boardName);
            preparedStatement.execute();

            for (int x = 0; x < sudokuBoard.size; x++) {
                for (int y = 0; y < sudokuBoard.size; y++) {
                    preparedStatement = connection.prepareStatement(SQL_WRITE_FIELD);
                    preparedStatement.setString(1, boardName);
                    preparedStatement.setInt(2, x);
                    preparedStatement.setInt(3, y);
                    preparedStatement.setInt(4, sudokuBoard.getField(x, y).getValue());
                    preparedStatement.execute();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.error_sql, e);
        }
    }

    public void delete(final String boardName) throws DaoException {
        if (boardName == null) {
            throw new DaoException(DaoException.null_board);
        }
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);

            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(SQL_DELETE_BOARD);
            pstmt.setString(1, boardName);
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement(SQL_DELETE_FIELDS);
            pstmt.setString(1, boardName);
            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new DaoException(DaoException.error_sql);
        }
    }
}

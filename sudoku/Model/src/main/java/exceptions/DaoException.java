package exceptions;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

//EXCERCISE 11 PARTS ----------

public class DaoException extends ApplicationException {

    private static final ResourceBundle messages;

    public static final String null_file = "null.file";
    public static final String null_board = "null.sudoku";

    public static final String invalid_cast = "invalid.cast";

    public static final String error_open = "error.open";
    public static final String error_close = "error.close";
    public static final String error_sql = "error.sql";
    public static final String null_record = "null.record";


    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("logMessages", locale);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        String exceptionMessage;
        try {
            exceptionMessage = messages.getString(getMessage());
        } catch (MissingResourceException mre) {
            exceptionMessage = "No resource for " + getMessage() + "key";
        }
        return exceptionMessage;
    }
}
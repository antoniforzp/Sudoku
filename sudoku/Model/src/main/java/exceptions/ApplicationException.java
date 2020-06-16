package exceptions;

//EXCERCISE 11 PARTS ----------

public class ApplicationException extends Exception {

    public static final String null_resourceBundle = "null.resource";

    public ApplicationException(String message) {
        super(message);
    }

    ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
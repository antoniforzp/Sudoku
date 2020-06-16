package serialization;

import exceptions.ApplicationException;

import java.util.ResourceBundle;

abstract class DaoExtension<T> implements Dao<T>, AutoCloseable {

    //connect resourceBundle to DaoException
    private static final ResourceBundle bundle = ResourceBundle.getBundle("logMessages");

    DaoExtension() throws ApplicationException {
        if (bundle == null) {
            throw new ApplicationException(ApplicationException.null_resourceBundle);
        }
    }
}

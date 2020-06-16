package serialization;

import exceptions.DaoException;

public interface Dao<T> {
    T read(final String path) throws DaoException;

    void write(T obj, final String path) throws DaoException;
}

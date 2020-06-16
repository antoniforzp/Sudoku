package utility;

import java.util.ArrayList;

public class FixedList<T> extends ArrayList<T> {

    /**
     * Constructor with fixed size.
     * @param capacity fixed size.
     */
    public FixedList(int capacity) {
        super(capacity);
        for (int i = 0; i < capacity; i++) {
            super.add(null);
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot clear elements");
    }

    @Override
    public boolean add(T o) {
        throw new UnsupportedOperationException("Cannot be added, use set() instead.");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Cannot be added, use set() instead.");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Cannot be removed.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot be removed");
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Cannot be removed.");
    }
}
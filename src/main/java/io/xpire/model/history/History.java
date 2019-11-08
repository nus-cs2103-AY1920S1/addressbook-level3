package io.xpire.model.history;

import java.util.LinkedList;

/**
 * Generic abstract class that contains a LinkedList of type T and provides abstract methods to retrieve previous/next
 * elements in History.
 * @@author Kalsyc
 * @param <T> Type to be stored in History.
 */
public abstract class History<T> {

    protected final LinkedList<T> history = new LinkedList<>();

    public abstract T previous();

    public abstract T next();

    public abstract void save(T element);

}

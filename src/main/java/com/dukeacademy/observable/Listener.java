package com.dukeacademy.observable;

/**
 * Interface to represent an observer in an observer pattern.
 *
 * @param <T> the type parameter
 */
public interface Listener<T> {
    /**
     * On update.
     *
     * @param value the value
     */
    void onUpdate(T value);
}

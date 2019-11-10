package com.dukeacademy.observable;

/**
 * Interface to represent an observable in this native implementation of the observer pattern.
 *
 * @param <T> the type parameter of the object to be observed
 */
public interface Observable<T> {
    /**
     * Adds a listener that will be updated when the value of the observable changes
     *
     * @param listener the listener to be added
     */
    void addListener(Listener<T> listener);

    /**
     * Removes a listener that was previously added.
     *
     * @param listener the listener to be removed
     */
    void removeListener(Listener<T> listener);
}

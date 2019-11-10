package com.dukeacademy.observable;

/**
 * Interface to represent a listener in this native implementation of the observer pattern.
 *
 * @param <T> the type parameter of the object to be observed
 */
public interface Listener<T> {
    /**
     * The method called by this listener's observables when there is a new value updated.
     * @param value the new value
     */
    void onUpdate(T value);
}

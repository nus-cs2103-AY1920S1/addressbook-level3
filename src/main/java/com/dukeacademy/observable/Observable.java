package com.dukeacademy.observable;

/**
 * Interface to represent an observable in an observer pattern.
 */
public interface Observable<T> {
    void addListener(Listener<T> listener);
    void removeListener(Listener<T> listener);
}

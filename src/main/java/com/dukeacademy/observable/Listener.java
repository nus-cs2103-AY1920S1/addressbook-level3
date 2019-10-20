package com.dukeacademy.observable;

/**
 * Interface to represent an observer in an observer pattern.
 */
public interface Listener<T> {
    void onUpdate(T value);
}

package com.dukeacademy.testutil;

import com.dukeacademy.observable.Listener;

/**
 * Listener for testing purposes
 * @param <T> generic for type of data to listen to.
 */
public class TestListener<T> implements Listener<T> {
    private T latestValue;

    public T getLatestValue() {
        return this.latestValue;
    }

    @Override
    public void onUpdate(T value) {
        this.latestValue = value;
    }
}

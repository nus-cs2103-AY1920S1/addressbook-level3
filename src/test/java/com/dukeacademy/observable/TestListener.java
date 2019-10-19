package com.dukeacademy.observable;

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

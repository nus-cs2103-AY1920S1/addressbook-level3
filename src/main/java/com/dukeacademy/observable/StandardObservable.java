package com.dukeacademy.observable;

import java.util.HashSet;
import java.util.Optional;

public class StandardObservable<T> implements Observable<T> {
    private T value;
    private HashSet<Listener<T>> listeners;

    public StandardObservable() {
        value = null;
        listeners = new HashSet<>();
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(this.value);
    }

    public void setValue(T value) {
        this.value = value;
        this.listeners.forEach(listener -> listener.onUpdate(value));
    }

    @Override
    public void addListener(Listener<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<T> listener) {
        this.listeners.remove(listener);
    }
}

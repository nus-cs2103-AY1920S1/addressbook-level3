package com.dukeacademy.observable;

import java.util.HashSet;
import java.util.Optional;

/**
 * Standard implementation of the Observable interface.
 *
 * @param <T> the type parameter of the object to be observed
 */
public class StandardObservable<T> implements Observable<T> {
    private T value;
    private final HashSet<Listener<T>> listeners;

    /**
     * Instantiates a new Standard observable with no default value.
     */
    public StandardObservable() {
        value = null;
        listeners = new HashSet<>();
    }

    /**
     * Instantiates a new Standard observable with a starting default value.
     *
     * @param value the default value
     */
    public StandardObservable(T value) {
        this.value = value;
        listeners = new HashSet<>();
    }

    /**
     * Returns the current value stored in the Observable instance.
     * @return the current value stored in this Observable instance.
     */
    public Optional<T> getValue() {
        return Optional.ofNullable(this.value);
    }

    /**
     * Sets the current value of the Observable instance to a new value. Each of the Observable's listeners
     * will be notified of this change.
     * @param value the new value
     */
    public void setValue(T value) {
        this.value = value;
        this.listeners.forEach(listener -> listener.onUpdate(value));
    }

    /**
     * Deletes all previously registered Listeners in the Observable instance.
     */
    public void clearListeners() {
        this.listeners.clear();
    }

    @Override
    public void addListener(Listener<T> listener) {
        this.listeners.add(listener);
        listener.onUpdate(value);
    }

    @Override
    public void removeListener(Listener<T> listener) {
        this.listeners.remove(listener);
    }
}

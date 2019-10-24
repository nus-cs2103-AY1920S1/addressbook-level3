package seedu.billboard.commons.core.observable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Lightweight observable wrapper around a value T which allows for observers to be registered and notified to any
 * changes in the value T. The observers are not aware of changes in state of the T, and are only notified when
 * {@code ObservableData#SetValue} is called.
 *
 * @param <T> Value to be observed.
 */
public class ObservableData<T> {

    private T value;
    private Set<Observer<? super T>> observers = new HashSet<>();

    /**
     * Basic getter for the value T.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of T to the new value, then notifies all observers about the change. If the set value is the
     * same as the current value, observers will not be notified.
     *
     * @param value New value for T.
     */
    public void setValue(T value) {
        if (this.value == value) {
            return;
        }

        this.value = value;
        notifyObservers();
    }

    /**
     * Registers an {@code Observer} which will be notified of any changes to the value T. The observer's
     * {@code onChanged} method is called once attached to notify the observer of the current value.
     *
     * @param observer Observer to be registered. Must be non null.
     */
    public void observe(Observer<? super T> observer) {
        Objects.requireNonNull(observer);
        observers.add(observer);
        observer.onChanged(value);
    }

    /**
     * Removes the given observer.
     */
    public void removeObserver(Observer<? super T> observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (var observer : observers) {
            observer.onChanged(value);
        }
    }
}

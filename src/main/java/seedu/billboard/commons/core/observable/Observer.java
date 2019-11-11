package seedu.billboard.commons.core.observable;

/**
 * Functional interface representing an observer to an {@code ObservableData} which receives updates to changes in the
 * observable data.
 * @param <T> Type of data being observed.
 */
@FunctionalInterface
public interface Observer<T> {
    void onChanged(T t);
}

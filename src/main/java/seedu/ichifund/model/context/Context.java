package seedu.ichifund.model.context;

import java.util.function.Predicate;

/**
 * Context that determines how a class should be filtered.
 *
 * @param <T> Class of items to be filtered.
 */
public interface Context<T> {
    /** Returns a predicate corresponding to the context. */
    Predicate<T> getPredicate();

    /** Returns a suitably modified context that shows the given object.
     * 
     * @param object Object to be accommodated.
     */
    Context<T> getAccommodatingContext(T object);
}

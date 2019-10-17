package seedu.ichifund.model.context;

import java.util.function.Predicate;

/**
 * Context that determines how a class should be filtered.
 *
 * @param <T> Class of items to be filtered.
 */
public interface Context<T> {
    Predicate<T> getPredicate();
}

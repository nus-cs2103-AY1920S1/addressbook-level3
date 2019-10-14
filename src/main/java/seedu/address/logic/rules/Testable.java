package seedu.address.logic.rules;

import java.util.function.Predicate;

/**
 * Represents a predicate with hidden internal logic and the ability to be tested.
 */
public interface Testable extends Predicate<Object> {
}

package seedu.address.model.rule;

import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction} satisfies the given condition.
 */
public abstract class RulePredicate implements Predicate<Object> {
    @Override
    public abstract boolean test(Object o);
}

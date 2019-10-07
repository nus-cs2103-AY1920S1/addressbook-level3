package seedu.address.model.rule;

import java.util.function.Predicate;

public class RulePredicate implements Predicate<Object> {

    @Override
    public boolean test(Object o) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this;
    }
}

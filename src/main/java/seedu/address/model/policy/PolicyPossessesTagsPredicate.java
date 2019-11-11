package seedu.address.model.policy;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Policy} possesses all of the of the {@code Tag} given.
 */
public class PolicyPossessesTagsPredicate implements Predicate<Policy> {
    private final List<Tag> tags;

    public PolicyPossessesTagsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Policy policy) {
        return policy.getTags().containsAll(tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PolicyPossessesTagsPredicate // instanceof handles nulls
                && tags.equals(((PolicyPossessesTagsPredicate) other).tags)); // state check
    }

}

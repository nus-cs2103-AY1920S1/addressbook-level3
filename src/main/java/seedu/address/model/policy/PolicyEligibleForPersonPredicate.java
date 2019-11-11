package seedu.address.model.policy;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Policy} can be possessed by a given person.
 */
public class PolicyEligibleForPersonPredicate implements Predicate<Policy> {
    private final Person person;

    public PolicyEligibleForPersonPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Policy policy) {
        return policy.isEligible(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PolicyEligibleForPersonPredicate // instanceof handles nulls
                && person.equals(((PolicyEligibleForPersonPredicate) other).person)); // state check
    }

}

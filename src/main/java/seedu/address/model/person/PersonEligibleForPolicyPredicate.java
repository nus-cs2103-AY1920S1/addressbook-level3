package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.policy.Policy;

/**
 * Tests whether a {@code Person} can possess a given policy.
 */
public class PersonEligibleForPolicyPredicate implements Predicate<Person> {
    private final Policy policy;

    public PersonEligibleForPolicyPredicate(Policy policy) {
        this.policy = policy;
    }

    @Override
    public boolean test(Person person) {
        return policy.isEligible(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonEligibleForPolicyPredicate // instanceof handles nulls
                && policy.equals(((PersonEligibleForPolicyPredicate) other).policy)); // state check
    }

}

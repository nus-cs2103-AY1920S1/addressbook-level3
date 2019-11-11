package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.policy.Policy;


/**
 * Tests that a {@code Person} possesses all of the of the {@code Tag} given.
 */
public class PersonPossessesPolicyPredicate implements Predicate<Person> {
    private final Policy policy;

    public PersonPossessesPolicyPredicate(Policy policy) {
        this.policy = policy;
    }

    @Override
    public boolean test(Person person) {
        return person.getPolicies().contains(policy);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonPossessesPolicyPredicate // instanceof handles nulls
                && policy.equals(((PersonPossessesPolicyPredicate) other).policy)); // state check
    }

}

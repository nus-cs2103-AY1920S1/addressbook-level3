package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.ForceThreadInterruptException;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} matches the given keyword.
 */
public class PersonDisplayAllPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        if (Thread.currentThread().interrupted()) {
            throw new ForceThreadInterruptException();
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof PersonDisplayAllPredicate; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return "";
    }
}

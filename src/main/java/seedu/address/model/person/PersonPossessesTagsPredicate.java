package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Person} possesses all of the of the {@code Tag} given.
 */
public class PersonPossessesTagsPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    public PersonPossessesTagsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().containsAll(tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonPossessesTagsPredicate // instanceof handles nulls
                && tags.equals(((PersonPossessesTagsPredicate) other).tags)); // state check
    }

}

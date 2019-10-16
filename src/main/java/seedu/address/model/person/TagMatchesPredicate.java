package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that any of a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagMatchesPredicate implements Predicate<Person> {

    private final String tagQuery;

    public TagMatchesPredicate(String tagQuery) {
        this.tagQuery = tagQuery;
    }

    @Override
    public boolean test(Person person) {
        boolean contains = false;
        for (Tag tag : person.getTags()) {
            if ((tag.tagName).equalsIgnoreCase(tagQuery)) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchesPredicate // instanceof handles nulls
                && tagQuery.equals(((TagMatchesPredicate) other).tagQuery)); // state check
    }
}

package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsTagPredicate implements Predicate<Person> {
    private final Set<Tag> tags;

    public PersonContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
    }

    // test on the person to see if he has the tag
    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(person::containsTag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagPredicate // instanceof handles nulls
                && tags.equals(((PersonContainsTagPredicate) other).tags)); // state check
    }

}
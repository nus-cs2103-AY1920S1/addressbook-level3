package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that any of a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final List<String> tagQueries;

    public TagMatchesPredicate(List<String> tagQueries) {
        this.tagQueries = tagQueries;
    }

    @Override
    public boolean test(Person person) {
        String allTags = person.getTags().toString()
                .replaceAll("\\[|\\]", "").replaceAll(",", " ");

        return tagQueries.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTags, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchesPredicate // instanceof handles nulls
                && tagQueries.equals(((TagMatchesPredicate) other).tagQueries)); // state check
    }
}

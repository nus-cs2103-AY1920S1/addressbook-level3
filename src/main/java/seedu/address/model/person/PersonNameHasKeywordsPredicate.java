package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonNameHasKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonNameHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonNameHasKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonNameHasKeywordsPredicate) other).keywords)); // state check
    }
}

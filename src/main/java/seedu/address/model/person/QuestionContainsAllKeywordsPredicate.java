package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Question} matches all of the keywords given.
 */
public class QuestionContainsAllKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public QuestionContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getQuestion().fullQuestion, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsAllKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}

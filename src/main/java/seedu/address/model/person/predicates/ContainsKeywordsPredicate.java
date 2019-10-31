package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} matches the given keyword.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public ContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsIgnoreCase(person.getName().toString(), keyword)
                    || StringUtil.containsIgnoreCase(person.getReferenceId().toString(), keyword)
                    || StringUtil.containsIgnoreCase(person.getPhone().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && StringUtil.containsIgnoreCase(keyword, ((ContainsKeywordsPredicate) other).keyword)); // state check
    }

}

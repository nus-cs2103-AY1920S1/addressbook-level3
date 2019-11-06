package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.ForceThreadInterruptException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} matches the given keyword.
 */
public class PersonMatchesKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public PersonMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword.toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        if (Thread.currentThread().interrupted()) {
            throw new ForceThreadInterruptException();
        }
        return person.getReferenceId().toString().toUpperCase().equals(keyword)
                    || person.getName().toString().toUpperCase().equals(keyword)
                    || person.getPhone().toString().toUpperCase().equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonMatchesKeywordPredicate // instanceof handles nulls
                && StringUtil.containsIgnoreCase(
                        keyword, ((PersonMatchesKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public String toString() {
        return String.format(
                "Displaying person whose ref id, name or phone number exactly matches\n'%1$s'", keyword);
    }
}

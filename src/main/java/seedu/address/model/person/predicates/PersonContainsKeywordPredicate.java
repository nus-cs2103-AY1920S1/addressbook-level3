//@@author SakuraBlossom
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.ForceThreadInterruptException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} contains the given keyword.
 */
public class PersonContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public PersonContainsKeywordPredicate(String keyword) {
        this.keyword = keyword.toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        if (Thread.currentThread().interrupted()) {
            throw new ForceThreadInterruptException();
        }
        return StringUtil.containsIgnoreCase(person.getName().toString(), keyword)
                    || StringUtil.containsIgnoreCase(person.getReferenceId().toString(), keyword)
                    || StringUtil.containsIgnoreCase(person.getPhone().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordPredicate // instanceof handles nulls
                && StringUtil.containsIgnoreCase(
                        keyword, ((PersonContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public String toString() {
        return String.format("Suggesting person(s) whose ref id, name or phone number contains\n'%1$s'", keyword);
    }
}

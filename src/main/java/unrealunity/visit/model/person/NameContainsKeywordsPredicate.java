package unrealunity.visit.model.person;

import java.util.function.Predicate;

import unrealunity.visit.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    //private final List<String> keywords;
    private final String keywords;

    /*
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    */

    public NameContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsPartialWordIgnoreCase(person.getName().fullName, keywords);
        //return keywords.stream()
        // .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

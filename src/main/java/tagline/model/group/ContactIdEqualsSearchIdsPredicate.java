//@@author e0031374
package tagline.model.group;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tagline.model.contact.Contact;

/**
 * Tests that a {@code Contact}'s {@code Name} matches any of the keywords given.
 */
public class ContactIdEqualsSearchIdsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public ContactIdEqualsSearchIdsPredicate(List<String> keywords) {
        this.keywords = keywords.stream()
            .mapToLong(Long::valueOf)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> keyword.equals(contact.getContactId().value.toString()));
        //.anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactIdEqualsSearchIdsPredicate // instanceof handles nulls
                && keywords.equals(((ContactIdEqualsSearchIdsPredicate) other).keywords)); // state check
    }

}

package seedu.address.model.events;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.common.ReferenceId;


/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public ContainsKeywordsPredicate(Event appointment) {
        ReferenceId referenceId = appointment.getPersonId();
        keywords = Arrays.asList((referenceId.toString().split("\\s+")));
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(keywords);
    }

    public ContainsKeywordsPredicate(ReferenceId referenceId) {
        keywords = Arrays.asList((referenceId.toString().split("\\s+")));
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(keywords);
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(event.getPersonId().toString(), keyword)
                                || (keyword.length() >= 3
                                && StringUtil.containsIgnoreCase(event.getPersonId().toString(), keyword))
                                || (keyword.length() >= 4
                                && StringUtil.containsIgnoreCase(event.getPersonId().toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }
}

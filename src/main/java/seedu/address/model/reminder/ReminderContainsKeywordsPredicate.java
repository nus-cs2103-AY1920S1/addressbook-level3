package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Reminder} matches any of the keywords given.
 */
public class ReminderContainsKeywordsPredicate implements Predicate<Reminder> {
    private final List<String> keywords;

    public ReminderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Reminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getDescription().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ReminderContainsKeywordsPredicate) other).keywords)); // state check
    }
}

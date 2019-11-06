package seedu.address.model.finance.logentry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.finance.attributes.Category;

/**
 * Tests that a {@code LogEntry}'s associated {@code Category}, matches the
 * categories given.
 * Not case-sensitive.
 */
public class LogEntryContainsCategoriesPredicate implements Predicate<LogEntry> {
    private final List<String> categories;

    public LogEntryContainsCategoriesPredicate(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public boolean test(LogEntry logEntry) {

        // If not specified, return all entries
        if (categories.size() == 0) {
            return true;
        }

        ArrayList<String> logEntryCategories = new ArrayList<String>();
        for (Category cat : logEntry.getCategories()) {
            logEntryCategories.add(cat.catName.toLowerCase());
        }

        return categories.stream()
                .anyMatch(cat -> logEntryCategories.contains(cat.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryContainsCategoriesPredicate // instanceof handles nulls
                && categories.equals(((LogEntryContainsCategoriesPredicate) other).categories)); // state check
    }

}

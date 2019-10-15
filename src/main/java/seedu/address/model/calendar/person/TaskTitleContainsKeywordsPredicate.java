package seedu.address.model.calendar.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;



/**
 * Tests that a {@code Task}'s {@code TaskTitle} matches any of the keywords given.
 */
public class TaskTitleContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TaskTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskTitle().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTitleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskTitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}

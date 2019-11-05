package seedu.address.model.task;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task}'s {@code Heading} matches any of the keywords given.
 */
public class TaskHeadingContainsKeyWordPredicate extends TaskPredicate {
    private final List<String> keywords;

    public TaskHeadingContainsKeyWordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getHeading().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskHeadingContainsKeyWordPredicate // instanceof handles nulls
                && keywords.equals(((TaskHeadingContainsKeyWordPredicate) other).keywords)); // state check
    }
}

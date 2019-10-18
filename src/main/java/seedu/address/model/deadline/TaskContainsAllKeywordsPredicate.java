//@@author dalsontws
package seedu.address.model.deadline;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Deadline}'s {@code Task} matches all of the keywords given.
 */
public class TaskContainsAllKeywordsPredicate implements Predicate<Deadline> {
    private final List<String> keywords;

    public TaskContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Deadline deadline) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(deadline.getTask().fullTask, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsAllKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}

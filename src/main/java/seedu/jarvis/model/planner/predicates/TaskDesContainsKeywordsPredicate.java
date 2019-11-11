package seedu.jarvis.model.planner.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests that the task description of a {@code Task} matches any of
 * the keywords given.
 */
public class TaskDesContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TaskDesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskDes(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskDesContainsKeywordsPredicate //instanceof handles nulls
                && keywords.equals(((TaskDesContainsKeywordsPredicate) other).keywords)); //state check
    }
}

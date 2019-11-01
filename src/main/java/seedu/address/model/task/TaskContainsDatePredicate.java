package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task} matches the date given.
 */
public class TaskContainsDatePredicate implements Predicate<Task> {
    private final String date;

    public TaskContainsDatePredicate(String date) {
        this.date = date;
    }

    @Override
    public boolean test(Task task) {
        return //task.getTime().stream().forEach(taskTime -> date.matches(taskTime.fullTime));
                task.getTime().stream().anyMatch(taskTime ->
                        StringUtil.containsWordIgnoreCase(taskTime.fullTime,
                                this.date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsDatePredicate // instanceof handles nulls
                && date.equals(((TaskContainsDatePredicate) other).date)); // state check
    }
}

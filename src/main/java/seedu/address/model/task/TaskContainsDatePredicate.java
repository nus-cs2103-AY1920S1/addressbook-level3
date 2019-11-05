package seedu.address.model.task;

import java.time.LocalDate;

/**
 * Tests that a {@code Task}'s {@code LocalDate} matches the date given.
 */
public class TaskContainsDatePredicate extends TaskPredicate {
    private final LocalDate date;

    public TaskContainsDatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Task task) {
        return this.date.equals(task.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsDatePredicate // instanceof handles nulls
                && date.equals(((TaskContainsDatePredicate) other).date)); // state check
    }
}

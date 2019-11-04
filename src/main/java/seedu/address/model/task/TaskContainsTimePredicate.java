package seedu.address.model.task;

import java.time.LocalTime;

/**
 * Tests that a {@code Task}'s {@code LocalTime} matches the time given.
 */
public class TaskContainsTimePredicate extends TaskPredicate {
    private final LocalTime time;

    public TaskContainsTimePredicate(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean test(Task task) {
        return this.time.equals(task.getTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsTimePredicate // instanceof handles nulls
                && time.equals(((TaskContainsTimePredicate) other).time)); // state check
    }
}

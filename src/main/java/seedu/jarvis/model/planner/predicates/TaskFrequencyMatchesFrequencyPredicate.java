package seedu.jarvis.model.planner.predicates;

import java.util.function.Predicate;

import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests that the frequency of a {@code Task} matches the frequency level given
 */
public class TaskFrequencyMatchesFrequencyPredicate implements Predicate<Task> {
    private final Frequency frequency;

    public TaskFrequencyMatchesFrequencyPredicate(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean test(Task task) {
        return task.getFrequency() == frequency;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskFrequencyMatchesFrequencyPredicate //instanceof handles nulls
                && frequency.equals(((TaskFrequencyMatchesFrequencyPredicate) other).frequency));
    }
}

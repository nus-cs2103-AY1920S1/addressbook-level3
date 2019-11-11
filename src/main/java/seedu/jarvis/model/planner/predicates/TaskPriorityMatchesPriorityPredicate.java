package seedu.jarvis.model.planner.predicates;

import java.util.function.Predicate;

import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests that the priority of a {@code Task} matches the priority level given
 */
public class TaskPriorityMatchesPriorityPredicate implements Predicate<Task> {
    private final Priority priority;

    public TaskPriorityMatchesPriorityPredicate(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean test(Task task) {
        return task.getPriority() == priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskPriorityMatchesPriorityPredicate //instanceof handles nulls
                && priority.equals(((TaskPriorityMatchesPriorityPredicate) other).priority));
    }
}

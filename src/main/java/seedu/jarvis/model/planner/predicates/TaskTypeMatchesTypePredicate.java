package seedu.jarvis.model.planner.predicates;

import java.util.function.Predicate;

import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Tests that the type of a {@code Task} matches the type of task given
 */
public class TaskTypeMatchesTypePredicate implements Predicate<Task> {
    private final TaskType type;

    public TaskTypeMatchesTypePredicate(TaskType type) {
        this.type = type;
    }


    @Override
    public boolean test(Task task) {
        return task.getTaskType() == type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskTypeMatchesTypePredicate //instanceof handles nulls
                && type.equals(((TaskTypeMatchesTypePredicate) other).type));
    }
}

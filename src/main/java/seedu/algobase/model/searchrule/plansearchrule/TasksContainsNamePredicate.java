package seedu.algobase.model.searchrule.plansearchrule;

import java.util.function.Predicate;

import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Name;

/**
 * Tests that a {@code Plan} contains {@code Task}s' names with matching {@code Keywords}.
 */
public class TasksContainsNamePredicate implements Predicate<Plan> {

    public static final TasksContainsNamePredicate DEFAULT_TASKS_CONTAINS_NAME_PREDICATE =
        new TasksContainsNamePredicate() {
            @Override
            public boolean test(Plan plan) {
                return true;
            }
        };

    private final Name name;

    public TasksContainsNamePredicate(Name name) {
        this.name = name;
    }

    private TasksContainsNamePredicate() {
        this.name = null;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean test(Plan plan) {
        assert name != null;
        return plan.getTasks().stream()
                .anyMatch(task -> name.fullName.equals(task.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        assert name != null;
        return other == this
                || (other instanceof TasksContainsNamePredicate
                && name.equals(((TasksContainsNamePredicate) other).getName()));
    }

}

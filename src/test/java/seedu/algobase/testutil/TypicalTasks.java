package seedu.algobase.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.algobase.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task QUICK_SORT_TASK = new TaskBuilder()
        .withProblem(new ProblemBuilder(TypicalProblems.QUICK_SORT).build())
        .build();

    public static final Task TWO_SUM_TASK = new TaskBuilder()
        .withProblem(new ProblemBuilder(TypicalProblems.TWO_SUM).build())
        .build();

    public static final Task FACTORIAL_TASK = new TaskBuilder()
        .withProblem(new ProblemBuilder(TypicalProblems.FACTORIAL).build())
        .build();

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(QUICK_SORT_TASK, TWO_SUM_TASK, FACTORIAL_TASK));
    }

}

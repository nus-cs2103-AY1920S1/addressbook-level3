package seedu.jarvis.testutil.planner;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Todo TODO = new TaskBuilder()
            .withDescription("Revise CS2103T")
            .buildTodo();
    public static final Deadline DEADLINE = new TaskBuilder()
            .withDescription("V1.4")
            .withDeadline("11/11/2019")
            .buildDeadline();
    public static final Event EVENT = new TaskBuilder()
            .withDescription("CS2103T Finals")
            .withStart("29/11/2019")
            .withEnd("29/11/2019")
            .buildEvent();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code Planner} with all the typical tasks.
     */
    public static Planner getTypicalPlanner() {
        Planner planner = new Planner();
        for (Task task : getTypicalTasks()) {
            planner.addTask(task);
        }
        return planner;
    }

    /**
     * Gets {@code List<Task>} from a typical planner.
     */
    public static List<Task> getTypicalTasks() {
        return Arrays.asList(TODO, DEADLINE, EVENT);
    }
}

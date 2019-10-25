package seedu.jarvis.testutil.planner;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final Task TODO = new TaskBuilder().withDescription("Revise CS2103T").withPriority("HIGH")
            .withFrequency("DAILY").withTags("Revision", "CS2103T").buildTodo();
    public static final Task DEADLINE = new TaskBuilder().withDescription("V1.4").withPriority("HIGH")
            .withDeadline("11/11/2019").withTags("Project").buildDeadline();
    public static final Task EVENT = new TaskBuilder().withDescription("CS2103T Finals").withPriority("HIGH")
            .withStart("29/11/2019").withEnd("29/11/2019").withTags("Finals", "CS2103T").buildEvent();

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
        return new Arrays.asList(TODO, DEADLINE, EVENT);
    }
}

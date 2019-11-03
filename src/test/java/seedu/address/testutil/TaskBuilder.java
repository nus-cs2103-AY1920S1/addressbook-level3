package seedu.address.testutil;

import java.util.TreeSet;

import seedu.address.model.classid.ClassId;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_CLASSID = "Tutorial 4";
    public static final String DEFAULT_MARKING = "Y";

    private ClassId classid;
    private Marking marking;
    private TreeSet<TaskTime> taskTimes;

    public TaskBuilder() {
        classid = new ClassId(DEFAULT_CLASSID);
        marking = new Marking(DEFAULT_MARKING);
        taskTimes = new TreeSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        classid = taskToCopy.getClassId();
        marking = taskToCopy.getMarking();
        taskTimes = taskToCopy.getTime();
    }

    /**
     * Sets the {@code classId} of the {@code Task} that we are building.
     */
    public TaskBuilder withClassId(String classId) {
        this.classid = new ClassId(classId);
        return this;
    }

    /**
     * Sets the {@code marking} of the {@code Task} that we are building.
     */
    public TaskBuilder withMarking(String marking) {
        this.marking = new Marking(marking);
        return this;
    }

    /**
     * Parses the {@code taskTimes} into a {@code Set<TaskTime>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTaskTimes(String ... taskTimes) {
        this.taskTimes = SampleDataUtil.getTaskTimeSet(taskTimes);
        return this;
    }

    public Task build() {
        return new Task(classid, taskTimes, marking);
    }
}

package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;
import seedu.address.model.calendar.task.ToDoTask;
import seedu.address.model.calendar.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "CS2110 Assignment";
    public static final String DEFAULT_TIME = "10:00";
    public static final String DEFAULT_DESCRIPTION = "Write by hand";
    public static final String DEFAULT_DAY = "monday";
    public static final String DEFAULT_DEADLINE = "01-09-2020";
    public static final int DEFAULT_WEEK = 0;

    private TaskTitle title;
    private TaskTime time;
    private TaskDescription description;
    private TaskDeadline deadline;
    private TaskDay day;
    private Set<TaskTag> tags;
    private int week;

    public TaskBuilder() {
        title = new TaskTitle(DEFAULT_TITLE);
        time = new TaskTime(DEFAULT_TIME);
        description = new TaskDescription(DEFAULT_DESCRIPTION);
        deadline = new TaskDeadline(DEFAULT_DEADLINE);
        day = new TaskDay(DEFAULT_DAY);
        tags = new HashSet<>();
        week = DEFAULT_WEEK;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(Task personToCopy) {
        title = personToCopy.getTaskTitle();
        time = personToCopy.getTaskTime();
        description = personToCopy.getTaskDescription();
        deadline = personToCopy.getTaskDeadline();
        day = personToCopy.getTaskDay();
        tags = new HashSet<>(personToCopy.getTaskTags());
        week = personToCopy.getWeek();
    }

    /**
     * Sets the {@code title} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskTitle(String title) {
        this.title = new TaskTitle(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTaskTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code TaskDay} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDay(String place) {
        this.day = new TaskDay(place);
        return this;
    }

    /**
     * Sets the {@code TaskTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskTime(String time) {
        this.time = new TaskTime(time);
        return this;
    }

    /**
     * Sets the {@code TaskTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDeadline(String deadline) {
        this.deadline = new TaskDeadline(deadline);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDescription(String description) {
        this.description = new TaskDescription(description);
        return this;
    }

    /**
     * Sets the {@code week} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskWeek(int week) {
        this.week = week;
        return this;
    }

    public Task build() {
        return new ToDoTask(title, day, description, deadline, time, tags, week);
    }
}

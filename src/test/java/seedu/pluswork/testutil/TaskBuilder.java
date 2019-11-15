package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;
import seedu.pluswork.model.util.SampleTaskDataUtil;

/**
 * A utility class to help with building {@code Task} objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Sample Task Name";

    private Name name;
    private TaskStatus taskStatus;
    private Set<Tag> tags;
    private LocalDateTime deadline;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        taskStatus = TaskStatus.UNBEGUN;
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        requireNonNull(taskToCopy);
        name = taskToCopy.getName();
        taskStatus = taskToCopy.getTaskStatus();
        tags = new HashSet<>(taskToCopy.getTags());
        if (taskToCopy.hasDeadline()) {
            deadline = taskToCopy.getDeadline();
        }
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withStatus(TaskStatus taskStatus) {
        this.taskStatus = TaskStatus.valueOf(taskStatus.toString());
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleTaskDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code LocalDateTime deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(LocalDateTime dateTime) {
        this.deadline = dateTime;
        return this;
    }

    public Task build() {
        return new Task(name, taskStatus, tags, deadline);
    }

}

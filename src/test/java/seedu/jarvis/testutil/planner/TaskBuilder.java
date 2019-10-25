package seedu.jarvis.testutil.planner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.model.util.SampleDataUtil;

/**
 * A utility class to help with building {@code Task} objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "Default description";

    private String description;
    private Priority priority;
    private Frequency frequency;
    private Set<Tag> tags;

    private LocalDate deadline;

    private LocalDate start;
    private LocalDate end;

    public TaskBuilder() {
        description = DEFAULT_DESCRIPTION;
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code task}.
     */
    public TaskBuilder(Task task) {
        description = task.getTaskDescription();
        priority = task.getPriority();
        frequency = task.getFrequency();
        tags = new HashSet<>(task.getTags());

        if (task instanceof Deadline) {
            deadline = ((Deadline) task).getDueDate();
        }

        if (task instanceof Event) {
            start = ((Event) task).getStartDate();
            end = ((Event) task).getEndDate();
        }
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = Priority.valueOf(priority);
        return this;
    }

    /**
     * Sets the {@code Frequency} of the {@code Task} that we are building.
     */
    public TaskBuilder withFrequency(String frequency) {
        this.frequency = Frequency.valueOf(frequency);
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String date) {
        deadline = LocalDate.parse(date, Task.getDateFormat());
        return this;
    }

    /**
     * Sets the {@code start} of the {@code Task} that we are building.
     */
    public TaskBuilder withStart(String date) {
        start = LocalDate.parse(date, Task.getDateFormat());
        return this;
    }

    /**
     * Sets the {@code end} of the {@code Task} that we are building.
     */
    public TaskBuilder withEnd(String date) {
        end = LocalDate.parse(date, Task.getDateFormat());
        return this;
    }

    /**
     * Builds a {@code Todo}.
     */
    public Task buildTodo() {
        return new Todo(description, priority, frequency, tags);
    }

    /**
     * Builds a {@code Deadline}.
     */
    public Task buildDeadline() {
        return new Deadline(description, priority, frequency, tags, deadline);
    }

    /**
     * Builds a {@code Event}.
     */
    public Task buildEvent() {
        return new Event(description, priority, frequency, tags, start, end);
    }
}

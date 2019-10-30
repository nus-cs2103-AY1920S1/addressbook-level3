package seedu.jarvis.model.planner.tasks;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.storage.planner.JsonAdaptedTask;

/**
 * Represents a task object in JARVIS
 */
public abstract class Task {
    //add t/TASK TYPE/TASK DES [d/DATE] [#TAG]... [p/PRIORITY LEVEL] [r/FREQ]

    public static final String EVENT = "event";
    public static final String DEADLINE = "deadline";
    public static final String TODO = "todo";
    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected String taskDes;
    protected Priority priority;
    protected Frequency frequency;
    protected Status status;
    protected Set<Tag> tags = new HashSet<>();

    public Task(String taskDes, Priority priority, Frequency frequency, Status status, Set<Tag> tags) {
        this.taskDes = taskDes;
        this.priority = priority;
        this.frequency = frequency;
        this.status = Status.NOT_DONE;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public Task(String taskDes) {
        this(taskDes, null, null, Status.NOT_DONE, new HashSet<>());
    }


    /**
     * Gets the task description of this {@code Todo}.
     * @return {@code String} containing the description of this {@code Todo}.
     */
    public String getTaskDescription() {
        return taskDes;
    }

    /**
     * Gets the {@code Priority} of this {@code Todo}.
     * @return {@code Priority} of the {@code Todo}.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Gets the {@code Frequency} of the {@Code Todo}.
     * @return {@code Frequency} of the {@code Todo}.
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * Gets the {@code JsonAdaptedTask} for this task.
     *
     * @return {@code JsonAdaptedTask}.
     */
    public abstract JsonAdaptedTask adaptToJsonAdaptedTask();

    public abstract String toString();

    /**
     * Retrieves the {@code TaskType} of the task
     * @return {@TaskType} of the task
     */
    public abstract TaskType getTaskType();

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public abstract boolean equals(Object other);

    /**
     * Sets the Priority Level of a Task
     * @param priority User input priority level
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the frequency level of a Task, i.e. how regularly a Task occurs.
     * @param freq Frequency level of a task
     */
    public void setFrequency(Frequency freq) {
        frequency = freq;
    }

    /**
     * Adds a Tag to the set of Tags attached to each Task
     * @param t Tag to be added
     */
    public void addTag(Tag t) {
        this.tags.add(t);
    }

    /**
     * Retrieves all the tags tagged to a particular task
     * @return a set of Tags
     */
    public Set<Tag> getTags() {
        return this.tags;
    }

    /**
     * Retrieves the DateTimeFormatter used for Event and Deadline tasks
     */
    public static DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    /**
     * Retrieves the task description of a task
     * @return the task description of a particular task
     */
    public String getTaskDes() {
        return taskDes;
    }

    /**
     * To show all the attributes (priority, frequency and tags) present in a task
     * It will not return any attributes if the task does not contain any, i.e. it will not show null
     * @return a string of all the attributes present in a task.
     */
    protected String attributesString() {
        String priority = this.priority == null ? "" : "\nPriority: " + this.priority;
        String frequency = this.frequency == null ? "" : "\nFrequency: " + this.frequency;
        String tags = this.tags.isEmpty() ? "" : "\nTags: " + getTags();

        return priority + frequency + tags;
    }

    /**
     * Marks a {@code Task} as done
     */
    public void markAsDone() {
        status = Status.DONE;
    }

    /**
     * Marks a {@code Task} as not done
     */
    public void markAsNotDone() {
        status = Status.NOT_DONE;
    }

    /**
     * Retrives the status of the object
     * @return DONE or NOT_DONE
     */
    public Status getStatus() {
        return status;
    }
}

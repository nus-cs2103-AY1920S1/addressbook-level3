package seedu.jarvis.model.planner.tasks;

import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;

/**
 * Represents a task object in JARVIS
 */
public abstract class Task {
    //add t/TASK TYPE/TASK DES [d/DATE] [#TAG]... [p/PRIORITY LEVEL] [r/FREQ]

    /**
     * Values that a priority of a task can take
     */
    enum PriorityLevel {
        HIGH,
        MED,
        LOW
    }

    /**
     * Values that a frequency level can take
     */
    enum FreqLevel {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    protected String taskDes;
    protected Set<Tag> tags = new HashSet<>();
    protected PriorityLevel priority = null;
    protected FreqLevel frequency = null;



    public Task(String taskDes) {
        this.taskDes = taskDes;
    }

    public abstract String toString();

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    protected abstract boolean isEqual(Task other);

    /**
     * Sets the Priority Level of a Task
     * @param priority User input priority level
     */
    protected void addPriority(String priority) throws InvalidPriorityException {
        switch (priority) {
        case "high":
            this.priority = PriorityLevel.HIGH;
            break;
        case "med":
            this.priority = PriorityLevel.MED;
            break;
        case "low":
            this.priority = PriorityLevel.LOW;
            break;
        default:
            throw new InvalidPriorityException();
        }
    }

    /**
     * Sets the frequency level of a Task, i.e. how regularly a Task occurs.
     * @param freq Frequency level of a task
     */
    protected void addFrequency(String freq) throws InvalidFrequencyException {
        switch (freq) {
        case "daily":
            this.frequency = FreqLevel.DAILY;
            break;
        case "weekly":
            this.frequency = FreqLevel.WEEKLY;
            break;
        case "monthly":
            this.frequency = FreqLevel.MONTHLY;
            break;
        case "yearly":
            this.frequency = FreqLevel.YEARLY;
            break;
        default:
            throw new InvalidFrequencyException();
        }
    }

    /**
     * Adds a Tag to the set of Tags attached to each Task
     * @param t Tag to be added
     */
    protected void addTag(Tag t) {
        this.tags.add(t);
    }

    /**
     * Retrieves all the tags tagged to a particular task
     * @return a set of Tags
     */
    protected Set getTags() {
        return this.tags;
    }

}

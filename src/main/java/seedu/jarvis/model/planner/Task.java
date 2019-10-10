package seedu.jarvis.model.planner;

import seedu.jarvis.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a task object in JARVIS
 */
public abstract class Task {
    //add t/TASK TYPE/TASK DES [d/DATE] [#TAG]... [p/PRIORITY LEVEL] [r/FREQ]

    enum priorityLevel {
        HIGH,
        MED,
        LOW
    }

    enum freqLevel {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    protected String task_des;
    protected Set<Tag> tags = new HashSet<>();
    protected priorityLevel priority = null;
    protected freqLevel frequency = null;



    public Task(String task_des) {
        this.task_des = task_des;
    }

    public abstract String toString();

    /**
     * Sets the Priority Level of a Task
     * @param priority User input priority level
     */
    protected void addPriority(String priority) {
        switch (priority) {
        case "high":
            this.priority = priorityLevel.HIGH;
            break;
        case "med":
            this.priority = priorityLevel.MED;
            break;
        case "low":
            this.priority = priorityLevel.LOW;
            break;
        default:
            break;
        }
    }

    /**
     * Sets the frequency level of a Task, i.e. how regularly a Task occurs.
     * @param freq Frequency level of a task
     */
    protected void addFrequency(String freq) {
        switch (freq) {
        case "daily":
            this.frequency = freqLevel.DAILY;
            break;
        case "weekly":
            this.frequency = freqLevel.WEEKLY;
            break;
        case "monthly":
            this.frequency = freqLevel.MONTHLY;
            break;
        case "yearly":
            this.frequency = freqLevel.YEARLY;
            break;
        default:
            break;
        }
    }

    /**
     * Adds a Tag to the set of Tags attached to each Task
     * @param t: a Tag to be added
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

package seedu.jarvis.model.planner.tasks;

import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

/**
 * Represents a task object in JARVIS
 */
public abstract class Task {
    //add t/TASK TYPE/TASK DES [d/DATE] [#TAG]... [p/PRIORITY LEVEL] [r/FREQ]

    protected String taskDes;
    protected Set<Tag> tags = new HashSet<>();
    protected Priority priority = null;
    protected Frequency frequency = null;

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
    public abstract boolean isEqual(Task other);

    /**
     * Sets the Priority Level of a Task
     * @param priority User input priority level
     */
    public void addPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the frequency level of a Task, i.e. how regularly a Task occurs.
     * @param freq Frequency level of a task
     */
     public void addFrequency(Frequency freq) {
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
    protected Set getTags() {
        return this.tags;
    }

}

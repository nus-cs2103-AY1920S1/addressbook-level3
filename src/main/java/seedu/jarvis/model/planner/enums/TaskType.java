package seedu.jarvis.model.planner.enums;

/**
 * Represents the type of task in the planner
 */
public enum TaskType {
    /**
     * Values that TaskType can take
     */
    TODO,
    EVENT,
    DEADLINE;

    @Override
    public String toString() {
        if (this == TODO) {
            return "Todo";
        } else if (this == EVENT) {
            return "Event";
        } else {
            return "Deadline";
        }
    }
}

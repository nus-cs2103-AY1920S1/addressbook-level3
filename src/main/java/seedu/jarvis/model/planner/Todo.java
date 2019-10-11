package seedu.jarvis.model.planner;

/**
 * Represents a Todo task in JARVIS
 */
public class Todo extends Task {

    public Todo(String taskDes) {
        super(taskDes);
    }

    @Override
    public String toString() {
        return "Todo: " + this.taskDes;
    }
}

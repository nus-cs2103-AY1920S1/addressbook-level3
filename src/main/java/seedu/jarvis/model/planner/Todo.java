package seedu.jarvis.model.planner;

/**
 * Represents a Todo task in JARVIS
 */
public class Todo extends Task {

    public Todo(String task_des) {
        super(task_des);
    }

    @Override
    public String toString() {
        return "Todo: " + this.task_des;
    }
}

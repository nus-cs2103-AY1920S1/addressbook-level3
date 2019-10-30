package organice.model.person;

/**
 * Handles the tasks
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String date;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String getDescription() {
        return (description);
    }

    public void markAsDone(Task t) {
        t.isDone = true;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        //Date date = new Date(2019,12,9,12,05);
        return date;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + description;
    }

}

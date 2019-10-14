package seedu.address.model.module;

/**
 * Start Time of the Lesson.
 */
public class StartTime {
    private String startTime;

    public StartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return startTime;
    }
}

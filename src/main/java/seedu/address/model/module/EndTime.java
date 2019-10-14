package seedu.address.model.module;

/**
 * End Time of the Lesson.
 */
public class EndTime {
    private String endTime;

    public EndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return endTime;
    }
}

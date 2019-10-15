package seedu.address.model.module;

/**
 * Day of the Lesson.
 */
public class Day {
    private String day;

    public Day(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return day;
    }
}

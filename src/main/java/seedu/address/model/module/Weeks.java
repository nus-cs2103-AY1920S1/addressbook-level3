package seedu.address.model.module;

/**
 * Weeks of the Lesson.
 */
public class Weeks {
    private String weeks;

    public Weeks(String weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        return weeks;
    }
}

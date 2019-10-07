package seedu.address.model.module;

/**
 * Exam Duration for a Module in a Semester.
 */
public class ExamDuration {
    private String examDuration;

    public ExamDuration(String examDuration) {
        this.examDuration = examDuration;
    }

    @Override
    public String toString() {
        return examDuration;
    }
}

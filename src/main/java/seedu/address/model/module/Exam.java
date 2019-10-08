package seedu.address.model.module;

/**
 * Exam for a Module in a Semester.
 */
public class Exam {
    private String examDate;
    private String examDuration;

    public Exam(String examDate, String examDuration) {
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    @Override
    public String toString() {
        return "Exam Date: " + examDate + " " + examDuration;
    }
}

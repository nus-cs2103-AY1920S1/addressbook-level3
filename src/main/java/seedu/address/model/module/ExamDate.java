package seedu.address.model.module;

/**
 * Exam Date for a Module in a Semester.
 */
public class ExamDate {
    private String examDate;

    public ExamDate(String examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return examDate;
    }
}

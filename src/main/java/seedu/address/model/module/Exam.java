package seedu.address.model.module;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Exam for a Module in a Semester.
 */
public class Exam {
    private final LocalDateTime examDate;
    private final int examDuration;

    public Exam(LocalDateTime examDate, int examDuration) {
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public int getExamDuration() {
        return examDuration;
    }

    @Override
    public String toString() {
        return "Exam Date: " + examDate.toString() + " " + Integer.toString(examDuration);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Exam)) {
            return false;
        }
        Exam exam = (Exam) other;
        if (exam == this) {
            return true;
        } else if (exam.examDate.equals(this.examDate)
                && exam.examDate.equals(this.examDuration)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(examDate, examDuration);
    }
}

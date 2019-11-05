package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Exam for a Module in a Semester.
 */
public class Exam {
    private final LocalDateTime examDate;
    private final int examDuration;

    public Exam(LocalDateTime examDate, int examDuration) {
        requireNonNull(examDate);
        requireNonNull(examDuration);
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
        return "Exam Date: " + examDate.toString() + " " + examDuration;
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
                && exam.examDuration == this.examDuration) {
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

package seedu.module.model.module;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents semester details pertaining to a module.
 */
public class SemesterDetail {

    private final int semester;
    private final Optional<LocalDateTime> examDate; // ISO standard datetime format
    private final int examDuration; // duration in minutes
    private final boolean offered; // whether the module is offered in this semester

    /**
     * Use this constructor to indicate the semester is offered.
     * @apiNote examDate may be null to indicate no exams for this semester.
     */
    public SemesterDetail(int semester, LocalDateTime examDate, int examDuration) {
        this.semester = semester;
        this.examDate = Optional.ofNullable(examDate);
        this.examDuration = examDuration;
        this.offered = true;
    }

    public SemesterDetail(int semester) {
        this.semester = semester;
        this.examDate = Optional.empty();
        this.examDuration = 0;
        this.offered = false;
    }

    public boolean isOffered() {
        return offered;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public Optional<LocalDateTime> getExamDate() {
        return examDate;
    }

    public int getSemester() {
        return semester;
    }

    // Compares 2 objects based on the identify field {@code semester}.
    @Override
    public boolean equals(Object o) {
        if (o instanceof SemesterDetail) {
            return ((SemesterDetail) o).getSemester() == this.semester;
        }

        return false;
    }
}

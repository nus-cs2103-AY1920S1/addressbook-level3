package seedu.address.model.module;

import java.util.Objects;

/**
 * Lesson type of the Lesson.
 */
public class LessonType {
    private String lessonType;

    public LessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    @Override
    public String toString() {
        return lessonType;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LessonType)) {
            return false;
        }
        LessonType lt = (LessonType) other;
        if (lt == this) {
            return true;
        } else if (lt.lessonType.equals(this.lessonType)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonType);
    }
}

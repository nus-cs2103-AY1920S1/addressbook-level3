package seedu.address.model.module;

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
}

package seedu.address.model.module;

/**
 * Lesson number of the Lesson.
 */
public class LessonNo {
    private String lessonNo;

    public LessonNo(String lessonNo) {
        this.lessonNo = lessonNo;
    }

    @Override
    public String toString() {
        return lessonNo;
    }
}

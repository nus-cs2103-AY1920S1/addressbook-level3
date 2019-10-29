package seedu.address.model.module;

import java.util.Objects;

/**
 * Lesson number of the Lesson.
 */
public class LessonNo {
    private static final String VALIDATION_REGEX = "[a-zA-Z0-9]*"; // alphanumeric

    private String lessonNo;


    public LessonNo(String lessonNo) {
        this.lessonNo = lessonNo;
    }


    @Override
    public String toString() {
        return lessonNo;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LessonNo)) {
            return false;
        }
        LessonNo lNo = (LessonNo) other;
        if (lNo == this) {
            return true;
        } else if (lNo.lessonNo.equals(this.lessonNo)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonNo);
    }

    public static boolean isValidLesson(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}

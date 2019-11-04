package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Lesson number of the Lesson.
 */
public class LessonNo {
    public static final String MESSAGE_CONSTRAINTS =
            "Lesson number should only contain alphanumeric characters, and it should not be blank";

    private static final String VALIDATION_REGEX = "[a-zA-Z0-9]*"; // alphanumeric

    private String lessonNo;

    public LessonNo(String lessonNo) {
        requireNonNull(lessonNo);
        checkArgument(isValidLessonNo(lessonNo), MESSAGE_CONSTRAINTS);
        this.lessonNo = lessonNo;
    }

    /**
     * Returns true if a given string is a valid lesson no.
     */
    public static boolean isValidLessonNo(String test) {
        return test.matches(VALIDATION_REGEX);
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
}

package seedu.address.model.module;

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

    /**
     * Checks if this LessonNo is equal to other LessonNo.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(LessonNo other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.lessonNo)) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isValidLesson(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}

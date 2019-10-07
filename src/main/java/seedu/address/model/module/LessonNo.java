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

    /**
     * Checks if this LessonNo is equal to other SemesterNo.
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
}

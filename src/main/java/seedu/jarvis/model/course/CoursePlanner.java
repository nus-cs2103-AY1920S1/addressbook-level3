package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Wraps all data for the Course Planner.
 */
public class CoursePlanner {
    private UniqueCourseList uniqueCourseList;

    /** String to render to display */
    private String showString;

    public CoursePlanner() {
        uniqueCourseList = new UniqueCourseList();
        showString = "";
    }

    public CoursePlanner(CoursePlanner coursePlanner) {
        this();
        resetData(coursePlanner);
    }

    /**
     * Resets the existing data of this {@code CoursePlanner} with {@code newData}.
     */
    public void resetData(CoursePlanner newData) {
        requireNonNull(newData);
        setUniqueCourseList(newData.getUniqueCourseList());
        setShowString(newData.getShowString());
    }

    public UniqueCourseList getUniqueCourseList() {
        return uniqueCourseList;
    }

    public String getShowString() {
        return showString;
    }

    public void setUniqueCourseList(UniqueCourseList uniqueCourseList) {
        this.uniqueCourseList = uniqueCourseList;
    }

    public void setShowString(String showString) {
        this.showString = showString;
    }

    public void lookUpCourse(Course code) {
        showString = code.toDisplayableString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoursePlanner that = (CoursePlanner) o;
        return Objects.equals(uniqueCourseList, that.uniqueCourseList)
                && Objects.equals(showString, that.showString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueCourseList, showString);
    }
}

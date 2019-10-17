package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;

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
        setUniqueCourseList(newData.getCourseList());
        setShowString(newData.getShowString());
    }

    public UniqueCourseList getUniqueCourseList() {
        return uniqueCourseList;
    }

    public String getShowString() {
        return showString;
    }

    public void setUniqueCourseList(List<Course> courses) {
        this.uniqueCourseList.setCourses(courses);
    }

    public void setShowString(String showString) {
        this.showString = showString;
    }

    public void lookUpCourse(Course course) {
        showString = course.toDisplayableString();
    }

    public boolean hasCourse(Course course) {
        return uniqueCourseList.contains(course);
    }

    public void addCourse(Course course) {
        uniqueCourseList.add(course);
    }

    public void addCourse(int zeroBasedIndex, Course course) {
        uniqueCourseList.add(zeroBasedIndex, course);
    }

    public void deleteCourse(Course course) {
        uniqueCourseList.remove(course);
    }

    public void setCourses(List<Course> courses) {
        uniqueCourseList.setCourses(courses);
    }

    public ObservableList<Course> getCourseList() {
        return uniqueCourseList.asUnmodifiableObservableList();
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

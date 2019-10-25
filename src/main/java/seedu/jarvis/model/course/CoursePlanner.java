package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.commons.util.andor.AndOrTree;

/**
 * Wraps all data for the Course Planner.
 */
public class CoursePlanner {
    private UniqueCourseList uniqueCourseList;
    private CourseDisplayText courseDisplayText;

    public CoursePlanner() {
        uniqueCourseList = new UniqueCourseList();
        courseDisplayText = new CourseDisplayText();
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
        setCourseDisplayText(newData.getText());
    }

    public UniqueCourseList getUniqueCourseList() {
        return uniqueCourseList;
    }

    public ObservableList<Course> getCourseList() {
        return uniqueCourseList.asUnmodifiableObservableList();
    }

    public CourseDisplayText getCourseDisplayText() {
        return courseDisplayText;
    }

    public String getText() {
        return courseDisplayText.get();
    }

    public String getText(int lineCharacterLimit) {
        return courseDisplayText.get(lineCharacterLimit);
    }

    public void setUniqueCourseList(List<Course> courses) {
        uniqueCourseList.setCourses(courses);
    }

    public void setCourses(List<Course> courses) {
        uniqueCourseList.setCourses(courses);
    }

    public void setCourseDisplayText(String text) {
        courseDisplayText.setValue(text);
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

    public void lookUpCourse(Course course) {
        courseDisplayText.setValue(course);
    }

    public void checkCourse(String toShow) {
        courseDisplayText.setValue(toShow);
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
        return Objects.equals(uniqueCourseList, that.uniqueCourseList) &&
            Objects.equals(courseDisplayText, that.courseDisplayText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueCourseList, courseDisplayText);
    }
}

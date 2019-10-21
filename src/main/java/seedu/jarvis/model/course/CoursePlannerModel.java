package seedu.jarvis.model.course;

import javafx.collections.ObservableList;

/**
 * The API of Course Planner component.
 */
public interface CoursePlannerModel {
    /**
     * Looks up a course.
     */
    void lookUpCourse(Course course);

    /**
     * Adds a course into the list.
     */
    void addCourse(Course course);

    /**
     * Adds a course based on the given zero-based index.
     */
    void addCourse(int zeroBasedIndex, Course course);

    /**
     * Removes a course and deletes it from the list.
     */
    void deleteCourse(Course course);

    /**
     * Returns true if a course exists inside the course planner.
     */
    boolean hasCourse(Course course);

    /**
     * Returns an unmodifiable list.
     */
    ObservableList<Course> getUnfilteredCourseList();

    /**
     * Gets this Model's course planner
     */
    CoursePlanner getCoursePlanner();
}

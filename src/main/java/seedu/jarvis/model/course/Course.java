package seedu.jarvis.model.course;

import java.util.Objects;

/**
 * Represents a Course in the course planner.
 */
public class Course {
    private final Title title;
    private final Faculty faculty;
    private final Description description;
    private final CourseCode courseCode;
    private final CourseCredit courseCredit;

    public Course(Title title, Faculty faculty, Description description,CourseCode courseCode,
                  CourseCredit courseCredit) {
        this.title = title;
        this.faculty = faculty;
        this.description = description;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
    }

    public Title getTitle() {
        return title;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Description getDescription() {
        return description;
    }

    public CourseCode getCourseCode() {
        return courseCode;
    }

    public CourseCredit getCourseCredit() {
        return courseCredit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, faculty, description, courseCode, courseCredit);
    }

    @Override
    public String toString() {
        // TODO implement toString when class is done
        final StringBuilder builder = new StringBuilder("");
        return builder.toString();
    }
}

package seedu.jarvis.testutil.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * Typical courses for testing use.
 */
public class TypicalCourses {
    // has all course attributes
    public static final Course CS3230 = new CourseBuilder()
        .withTitle("3230")
        .withCourseCode("CS3230")
        .withDescription("This is CS3230")
        .withFaculty("Computing")
        .withPrereqTree("{\"and\": [{\"or\": [\"CS2010\",\"CS2020\",\"CS2040\"]},{\"or\": [\"MA1100\",\"CS1231\"]}]}")
        .withCourseCredit(4)
        .withPreclusion("Some preclusions")
        .withFulfillRequirements("[\"CS5000\"]")
        .build();

    // does not have prereqTree or FulfillRequirements
    public static final Course MA1521 = new CourseBuilder()
        .withTitle("MA1521")
        .withCourseCode("MA1521")
        .withDescription("This is MA1521")
        .withFaculty("Mathematics")
        .withCourseCredit(4)
        .withPreclusion("MA1505 precludes")
        .build();

    // only has non-nullable attributes
    public static final Course CS1010 = new CourseBuilder()
        .withTitle("1010")
        .withCourseCode("CS1010")
        .withDescription("This is CS1010")
        .withFaculty("Computing")
        .withCourseCredit(4)
        .build();

    // private constructor to prevent instantiation
    private TypicalCourses() {}

    /**
     * Returns a {@code CoursePlanner} with typical courses pre-loaded.
     *
     * @return a new {@code CoursePlanner} object
     */
    public static CoursePlanner getTypicalCoursePlanner() {
        CoursePlanner coursePlanner = new CoursePlanner();
        for (Course course : getTypicalCourses()) {
            coursePlanner.addCourse(course);
        }
        return coursePlanner;
    }

    /**
     * Returns a {@code List} containing some typical courses.
     *
     * @return a {@code List} of courses
     */
    public static List<Course> getTypicalCourses() {
        return new ArrayList<>(Arrays.asList(MA1521, CS1010, CS3230));
    }
}

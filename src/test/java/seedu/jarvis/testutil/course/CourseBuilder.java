package seedu.jarvis.testutil.course;

import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CourseCode;
import seedu.jarvis.model.course.CourseCredit;
import seedu.jarvis.model.course.Description;
import seedu.jarvis.model.course.Faculty;
import seedu.jarvis.model.course.FulfillRequirements;
import seedu.jarvis.model.course.Preclusion;
import seedu.jarvis.model.course.PrereqTree;
import seedu.jarvis.model.course.Title;

/**
 * A utility class to build courses.
 */
public class CourseBuilder {
    public static final String DEFAULT_TITLE = "CourseTitle";
    public static final String DEFAULT_FACULTY = "Example Faculty";
    public static final String DEFAULT_COURSE_CODE = "CC1000";
    public static final Integer DEFAULT_COURSE_CREDIT = 4;
    public static final String DEFAULT_DESCRIPTION = "Example Description";

    private Title title;
    private Faculty faculty;
    private CourseCode courseCode;
    private CourseCredit courseCredit;
    private PrereqTree prereqTree;
    private FulfillRequirements fulfillRequirements;
    private Description description;
    private Preclusion preclusion;

    public CourseBuilder() {
        title = new Title(DEFAULT_TITLE);
        faculty = new Faculty(DEFAULT_FACULTY);
        courseCode = new CourseCode(DEFAULT_COURSE_CODE);
        courseCredit = new CourseCredit(DEFAULT_COURSE_CREDIT);
        description = new Description(DEFAULT_DESCRIPTION);

        // nullable
        preclusion = null;
        prereqTree = null;
        fulfillRequirements = null;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param title attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param faculty attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withFaculty(String faculty) {
        this.faculty = new Faculty(faculty);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param courseCode attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withCourseCode(String courseCode) {
        this.courseCode = new CourseCode(courseCode);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param courseCredit attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withCourseCredit(Integer courseCredit) {
        this.courseCredit = new CourseCredit(courseCredit);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param prereqTree attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withPrereqTree(String prereqTree) {
        this.prereqTree = new PrereqTree(prereqTree);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param fulfillRequirements attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withFulfillRequirements(String fulfillRequirements) {
        this.fulfillRequirements = new FulfillRequirements(fulfillRequirements);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param description attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Returns the current CourseBuilder with the new attribute set.
     *
     * @param preclusion attribute
     * @return the current CourseBuilder with a set attribute
     */
    public CourseBuilder withPreclusion(String preclusion) {
        this.preclusion = new Preclusion(preclusion);
        return this;
    }

    /**
     * Returns a new {@code Course} represented by this {@code CourseBuilder}
     *
     * @return the current CourseBuilder with a set attribute
     */
    public Course build() {
        return new Course(title, faculty, description, courseCode, courseCredit, prereqTree,
                preclusion, fulfillRequirements);
    }
}

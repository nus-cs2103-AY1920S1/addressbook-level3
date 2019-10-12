package seedu.jarvis.model.course;

import seedu.jarvis.commons.util.andor.AndOrTree;

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
    private final PrereqTree prereqTree;
    private final Preclusion preclusion;
    private final FulfillRequirements fulfillRequirements;

    public Course() {
        this.title = null;
        this.faculty = null;
        this.description = null;
        this.courseCode = null;
        this.courseCredit = null;
        this.prereqTree = null;
        this.preclusion = null;
        this.fulfillRequirements = null;
    }

    public Course(Title title, Faculty faculty, Description description, CourseCode courseCode,
                  CourseCredit courseCredit, PrereqTree prereqTree, Preclusion preclusion,
                  FulfillRequirements fulfillRequirements) {
        this.title = title;
        this.faculty = faculty;
        this.description = description;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
        this.prereqTree = prereqTree;
        this.fulfillRequirements = fulfillRequirements;
        this.preclusion = preclusion;
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

    public PrereqTree getPrereqTree() {
        return prereqTree;
    }

    public Preclusion getPreclusion() {
        return preclusion;
    }

    public FulfillRequirements getFulfillRequirements() {
        return fulfillRequirements;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, faculty, description, courseCode, courseCredit, prereqTree,
                preclusion, fulfillRequirements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return getCourseCode().equals(course.getCourseCode());
    }

    /**
     * Returns a more readable {@code String} to be displayed to the user as opposed to
     * this object's {@code toString} method.
     *
     * @return a displayable {@code String}
     */
    public String toDisplayableString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCourseCode()).append("\n")
                .append(getTitle()).append("\n")
                .append(getCourseCredit()).append(" MCs\n")
                .append("Offered by: ").append(getFaculty()).append("\n")
                .append(getFaculty()).append("\n")
                .append("Preclusion: ").append(getPreclusion()).append("\n")
                .append("Required for: ").append(getFulfillRequirements()).append("\n");
        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Title]: ").append(getTitle())
                .append(" [Faculty]: ").append(getFaculty())
                .append(" [Description]: ").append(getDescription())
                .append(" [CourseCode]: ").append(getCourseCode())
                .append(" [CourseCredit]: ").append(getCourseCredit())
                .append(" [PrereqTree]: ").append(getPrereqTree())
                .append(" [Preclusions]: ").append(getPreclusion())
                .append(" [FulfillRequirements]: ").append(getFulfillRequirements());
        return builder.toString();
    }
}

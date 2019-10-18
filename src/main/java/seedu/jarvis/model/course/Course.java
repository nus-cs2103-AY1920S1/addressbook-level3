package seedu.jarvis.model.course;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.jarvis.commons.util.StringUtil;


/**
 * Represents a Course in the course planner.
 */
public class Course {
    /** formatting strings */
    private static final String DISPLAYABLE_CODE_TITLE_FORMAT = "%s \"%s\"";
    private static final String DISPLAYABLE_CREDITS_FORMAT = "%s MCs";
    private static final String DISPLAYABLE_FACULTY_FORMAT = "Offered by: %s";
    private static final String DISPLAYABLE_PRECLUSION_FORMAT = "Preclusion: %s";
    private static final String DISPLAYABLE_FULFILLREQ_FORMAT = "Required for: %s";

    private final Title title;
    private final Faculty faculty;
    private final Description description;
    private final CourseCode courseCode;
    private final CourseCredit courseCredit;

    // can be null
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
        return Objects.hash(title, faculty, courseCode, courseCredit, description);
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
        final int limit = 100;
        List<String> toRender = new ArrayList<>();
        toRender.add(String.format(DISPLAYABLE_CODE_TITLE_FORMAT, getCourseCode(), getTitle()));
        toRender.add(String.format(DISPLAYABLE_CREDITS_FORMAT, getCourseCredit()));
        toRender.add(String.format(DISPLAYABLE_FACULTY_FORMAT, getFaculty()));
        if (!isNull(getPreclusion())) {
            toRender.add(String.format(DISPLAYABLE_PRECLUSION_FORMAT, getPreclusion()));
        }
        if (!isNull(getFulfillRequirements())) {
            toRender.add(String.format(DISPLAYABLE_FULFILLREQ_FORMAT, getFulfillRequirements()));
        }
        toRender.add("\n" + StringUtil.asLimitedCharactersPerLine(getDescription().toString(), limit));
        return StringUtil.listToString(toRender);
    }

    @Override
    public String toString() {
        return "[Title]: " + getTitle()
            + " [Faculty]: " + getFaculty()
            + " [Description]: " + getDescription()
            + " [CourseCode]: " + getCourseCode()
            + " [CourseCredit]: " + getCourseCredit()
            + " [PrereqTree]: " + getPrereqTree()
            + " [Preclusions]: " + getPreclusion()
            + " [FulfillRequirements]: " + getFulfillRequirements();
    }
}

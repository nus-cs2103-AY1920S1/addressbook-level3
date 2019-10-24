package seedu.jarvis.storage.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {
    private final String title;
    private final String faculty;
    private final String description;
    private final String courseCode;
    private final String courseCredit;

    // can be null.
    private final String prereqTree;
    private final String preclusion;
    private final String fulfillRequirements;

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given {@code Course}.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("title") String title, @JsonProperty("faculty") String faculty,
                             @JsonProperty("description") String description,
                             @JsonProperty("courseCode") String courseCode,
                             @JsonProperty("courseCredit") String courseCredit,
                             @JsonProperty("prereqTree") String prereqTree,
                             @JsonProperty("preclusion") String preclusion,
                             @JsonProperty("fulfillRequirements") String fulfillRequirements) {
        this.title = title;
        this.faculty = faculty;
        this.description = description;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
        this.prereqTree = prereqTree;
        this.preclusion = preclusion;
        this.fulfillRequirements = fulfillRequirements;
    }

    public JsonAdaptedCourse(Course course) {
        title = course.getTitle().title;
        faculty = course.getFaculty().faculty;
        description = course.getDescription().description;
        courseCode = course.getCourseCode().code;
        courseCredit = course.getCourseCredit().toString();
        prereqTree = course.getPrereqTree() != null ? course.getPrereqTree().tree : null;
        preclusion = course.getPreclusion() != null ? course.getPreclusion().preclusion : null;
        fulfillRequirements = course.getFulfillRequirements() != null
                ? course.getFulfillRequirements().fulfillRequirements
                : null;
    }

    /**
     * Converts this Jackson-friendly adapted {@code Course} object into the model's {@code Course} object.
     *
     * @return {@code Course} of the Jackson-friendly adapted {@code Course}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Course}.
     */
    public Course toModelType() throws IllegalValueException {
        return new Course(
                new Title(title),
                new Faculty(faculty),
                new Description(description),
                new CourseCode(courseCode),
                new CourseCredit(courseCredit),
                prereqTree != null ? new PrereqTree(prereqTree) : null,
                preclusion != null ? new Preclusion(preclusion) : null,
                fulfillRequirements != null ? new FulfillRequirements(fulfillRequirements) : null);
    }
}

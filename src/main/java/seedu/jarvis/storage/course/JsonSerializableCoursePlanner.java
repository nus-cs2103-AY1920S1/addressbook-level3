package seedu.jarvis.storage.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.storage.JsonAdapter;

/**
 * A {@code CoursePlanner} that is serializable to JSON format.
 */
@JsonRootName(value = "courseplanner")
public class JsonSerializableCoursePlanner implements JsonAdapter<CoursePlanner> {

    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCoursePlanner} with the given courses.
     */
    @JsonCreator
    public JsonSerializableCoursePlanner(@JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        this.courses.addAll(courses);
    }

    /**
     * Converts a given {@code CoursePlanner} into this class for Jackson use.
     *
     * @param coursePlanner Future changes to this will not affect the created {@code JsonSerializableCoursePlanner}.
     */
    public JsonSerializableCoursePlanner(CoursePlanner coursePlanner) {
        courses.addAll(coursePlanner.getCourseList().stream().map(JsonAdaptedCourse::new).collect(Collectors.toList()));
    }

    /**
     * Converts this {@code JsonAdaptedCoursePlanner} into the model's {@code CoursePlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    @Override
    public CoursePlanner toModelType() throws IllegalValueException {
        CoursePlanner coursePlanner = new CoursePlanner();
        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            coursePlanner.addCourse(jsonAdaptedCourse.toModelType());
        }
        return coursePlanner;
    }
}

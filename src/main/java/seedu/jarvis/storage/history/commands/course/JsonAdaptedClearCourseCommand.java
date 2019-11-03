package seedu.jarvis.storage.history.commands.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.course.ClearCourseCommand;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.course.JsonAdaptedCourse;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link ClearCourseCommand}.
 */
public class JsonAdaptedClearCourseCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedClearCourseCommand} with the given {@code Course} objects that was cleared.
     *
     * @param courses {@code Course} objects that were cleared in JSON format.
     */
    @JsonCreator
    public JsonAdaptedClearCourseCommand(@JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        this.courses.addAll(courses);
    }

    /**
     * Converts a given {@code ClearCourseCommand} into this class for Jackson use.
     *
     * @param clearCourseCommand {@code ClearCourseCommand} to be used to construct the
     * {@code JsonAdaptedClearCourseCommand}.
     */
    public JsonAdaptedClearCourseCommand(ClearCourseCommand clearCourseCommand) {
        courses.addAll(clearCourseCommand.getClearedCourses()
                .stream()
                .map(JsonAdaptedCourse::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted {@code ClearCourseCommand} object into the model's
     * {@code ClearCourseCommand} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code ClearCourseCommand}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code ClearCourseCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        List<Course> deletedCourses = new ArrayList<>();
        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            deletedCourses.add(jsonAdaptedCourse.toModelType());
        }
        return new ClearCourseCommand(deletedCourses);
    }
}

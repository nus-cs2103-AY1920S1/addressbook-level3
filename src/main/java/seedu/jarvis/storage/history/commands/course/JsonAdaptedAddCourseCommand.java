package seedu.jarvis.storage.history.commands.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.course.JsonAdaptedCourse;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link AddCourseCommand}.
 */
public class JsonAdaptedAddCourseCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAddCourseCommand} with the given {@code Course} objects to add.
     *
     * @param courses {@code Course} objects in Json format.
     */
    @JsonCreator
    public JsonAdaptedAddCourseCommand(@JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        this.courses.addAll(courses);
    }

    /**
     * Converts a given {@code AddCourseCommand} into this class for Jackson use.
     *
     * @param addCourseCommand {@code AddCourseCommand} to be used to construct the
     * {@code JsonAdaptedAddCourseCommand}.
     */
    public JsonAdaptedAddCourseCommand(AddCourseCommand addCourseCommand) {
        courses.addAll(addCourseCommand.getCoursesToAdd()
                .stream()
                .map(JsonAdaptedCourse::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddCourseCommand} object into the model's {@code AddCourseCommand}
     * object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddCourseCommand}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code AddCourseCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        List<Course> listOfCourses = new ArrayList<>();
        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            listOfCourses.add(jsonAdaptedCourse.toModelType());
        }
        return new AddCourseCommand(listOfCourses);
    }
}

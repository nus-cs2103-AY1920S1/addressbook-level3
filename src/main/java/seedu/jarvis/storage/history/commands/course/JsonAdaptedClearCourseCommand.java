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

public class JsonAdaptedClearCourseCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedClearCourseCommand(@JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        this.courses.addAll(courses);
    }

    public JsonAdaptedClearCourseCommand(ClearCourseCommand clearCourseCommand) {
        courses.addAll(clearCourseCommand.getClearedCourses()
                .stream()
                .map(JsonAdaptedCourse::new)
                .collect(Collectors.toList()));
    }

    @Override
    public Command toModelType() throws IllegalValueException {
        List<Course> deletedCourses = new ArrayList<>();
        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            deletedCourses.add(jsonAdaptedCourse.toModelType());
        }
        return new ClearCourseCommand(deletedCourses);
    }
}

package seedu.jarvis.storage.history.commands.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.course.JsonAdaptedCourse;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link DeleteCourseCommand}.
 */
public class JsonAdaptedDeleteCourseCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {
    private final JsonAdaptedCourse course;
    private final JsonAdaptedIndex targetIndex;

    /**
     * Constructs a {@code JsonAdaptedDeleteCourseCommand} with the given {@code Index} of the course to delete, and
     * {@code JsonAdaptedCourse} that was deleted.
     *
     * @param course {@code Course} that was deleted, which may be null.
     * @param targetIndex {@code Index} of the {@code Course} to be deleted.
     */
    @JsonCreator
    public JsonAdaptedDeleteCourseCommand(@JsonProperty("course") JsonAdaptedCourse course,
                               @JsonProperty("targetIndex") JsonAdaptedIndex targetIndex) {
        this.course = course;
        this.targetIndex = targetIndex;
    }

    /**
     * Converts a given {@code DeleteCourseCommand} into this class for Jackson use.
     *
     * @param deleteCourseCommand {@code DeleteCourseCommand} to be used to construct the
     *      * {@code JsonAdaptedDeleteCourseCommand}.
     */
    public JsonAdaptedDeleteCourseCommand(DeleteCourseCommand deleteCourseCommand) {
        course = deleteCourseCommand.getDeletedCourse().map(JsonAdaptedCourse::new).orElse(null);
        targetIndex = deleteCourseCommand.getTargetIndex().map(JsonAdaptedIndex::new).orElse(null);
    }


    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedDeleteCourseCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        return new DeleteCourseCommand(
                course != null ? course.toModelType() : null,
                targetIndex != null ? targetIndex.toModelType() : null);
    }
}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.tag.UserTag;

/**
 * Jackson-friendly version of {@link Semester}.
 */
class JsonAdaptedSemester {

    private final String semesterName;

    // TODO: this will change according to the actual model of Semester which is not yet implemented
    /**
     * Constructs a {@code JsonAdaptedSemester} with the given {@code semesterName}.
     */
    @JsonCreator
    public JsonAdaptedSemester(String semesterName) {
        this.semesterName = semesterName;
    }

    // TODO: this will change according to the actual model of Semester which is not yet implemented
    /**
     * Converts a given {@code Semester} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterName = source.getSemesterName().toString();
    }

    @JsonValue
    public String getSemesterName() {
        return semesterName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Semester toModelType() throws IllegalValueException {
        // TODO: change this later. the return value is hardcoded
        if (!UserTag.isValidTagName(semesterName)) {
            throw new IllegalValueException(UserTag.MESSAGE_CONSTRAINTS);
        }
        return new Semester(SemesterName.Y1S1);
    }

}

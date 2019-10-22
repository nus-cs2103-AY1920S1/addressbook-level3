package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Grade;

/**
 * Jackson-friendly version of {@link Grade}.
 */
class JsonAdaptedGrades {

    private final String marks;

    /**
     * Constructs a {@code JsonAdaptedGrades} with the given {@code grades}.
     */
    @JsonCreator
    public JsonAdaptedGrades(String grades) {
        this.marks = grades;
    }

    /**
     * Converts a given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrades(Grade source) {
        marks = source.value;
    }

    public JsonAdaptedGrades(Integer grades) {
        this.marks = grades + "";
    }

    @JsonValue
    public String getGrades() {
        return marks;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Grade toModelType() throws IllegalValueException {
        if (!Grade.isValidGrade(marks)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(marks);
    }

}

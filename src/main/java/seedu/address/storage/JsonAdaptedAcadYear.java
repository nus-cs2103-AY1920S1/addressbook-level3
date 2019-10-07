package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.SemesterNo;

/**
 * Jackson-friendly version of {@link AcadYear}.
 */
class JsonAdaptedAcadYear {

    private final String acadYear;

    /**
     * Constructs a {@code JsonAdaptedAcadYear}
     */
    @JsonCreator
    public JsonAdaptedAcadYear(String acadYear) {
        this.acadYear = acadYear;
    }

    /**
     * Converts a given {@code AcadYear} into this class for Jackson use.
     */
    public JsonAdaptedAcadYear(AcadYear source) {
        acadYear = source.getAcadYear();
    }

    @JsonValue
    public String getAcadYear() {
        return acadYear;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code AcadYear} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public AcadYear toModelType() throws IllegalValueException {
        return new AcadYear(acadYear);
    }

}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.VisitList;

/**
 * Jackson-friendly version of {@link VisitList}.
 */
class JsonAdaptedVisit {

    private final String visitDate;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedVisit(String date) {
        this.visitDate = date;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedVisit(VisitList source) {
        visitDate = source.value;
    }

    @JsonValue
    public String getVisitationRecord() {
        return visitDate;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public VisitList toModelType() throws IllegalValueException {
        if (!VisitList.isValidVisitDate(visitDate)) {
            throw new IllegalValueException(VisitList.MESSAGE_CONSTRAINTS);
        }
        return new VisitList(visitDate);
    }

}

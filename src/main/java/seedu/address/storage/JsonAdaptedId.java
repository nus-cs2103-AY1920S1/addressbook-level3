package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commonvariables.Id;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedId {

    private final String id;

    /**
     * Constructs a {@code JsonAdaptedId} with the given {@code id}.
     */
    @JsonCreator
    public JsonAdaptedId(String id) {
        this.id = id;
    }

    /**
     * Converts a given {@code Id} into this class for Jackson use.
     */
    public JsonAdaptedId(Id source) {
        id = source.value;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    /**
     * Converts this Jackson-friendly adapted id object into the model's {@code Id} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted id.
     */
    public Id toModelType() throws IllegalValueException {
        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(id);
    }

}

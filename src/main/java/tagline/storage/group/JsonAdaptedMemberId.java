//@@author e0031374
package tagline.storage.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.group.MemberId;

/**
 * Jackson-friendly version of {@link MemberId}.
 */
public class JsonAdaptedMemberId {

    private final String idValue;

    /**
     * Constructs a {@code JsonAdaptedContactId} with the given {@code idValue}.
     */
    @JsonCreator
    public JsonAdaptedMemberId(String idValue) {
        this.idValue = idValue;
    }

    /**
     * Converts a given {@code ContactId} into this class for Jackson use.
     */
    public JsonAdaptedMemberId(MemberId source) {
        idValue = source.value;
    }

    @JsonValue
    public String getMemberIdName() {
        return idValue;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ContactId} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public MemberId toModelType() throws IllegalValueException {
        if (!MemberId.isValidMemberId(idValue)) {
            throw new IllegalValueException(MemberId.MESSAGE_CONSTRAINTS);
        }
        return new MemberId(idValue);
    }

}

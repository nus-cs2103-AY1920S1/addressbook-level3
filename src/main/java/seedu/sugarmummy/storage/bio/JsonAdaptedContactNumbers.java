package seedu.sugarmummy.storage.bio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.model.bio.Phone;

/**
 * Jackson-friendly version of {@link Phone}.
 */
class JsonAdaptedContactNumbers {

    private final String contactNumber;

    /**
     * Constructs a {@code JsonAdaptedPhone} with the given {@code contactNumber}.
     */
    @JsonCreator
    public JsonAdaptedContactNumbers(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Converts a given {@code Phone} into this class for Jackson use.
     */
    public JsonAdaptedContactNumbers(Phone source) {
        contactNumber = source.phoneNumber;
    }

    @JsonValue
    public String getContactNumbers() {
        return contactNumber;
    }

    /**
     * Converts this Jackson-friendly adapted contact number object into the sugarmummy.recmfood.model's {@code Phone}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact number.
     */
    public Phone toModelType() throws IllegalValueException {
        if (!Phone.isValidPhone(contactNumber)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(contactNumber);
    }

}

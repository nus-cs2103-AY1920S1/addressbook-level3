package seedu.sugarmummy.storage.biography;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.model.biography.Phone;

/**
 * Jackson-friendly version of {@link Phone}.
 */
class JsonAdaptedEmergencyContacts {

    private final String emergencyContacts;

    /**
     * Constructs a {@code JsonAdaptedPhone} with the given {@code emergencyContacts}.
     */
    @JsonCreator
    public JsonAdaptedEmergencyContacts(String emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    /**
     * Converts a given {@code Phone} into this class for Jackson use.
     */
    public JsonAdaptedEmergencyContacts(Phone source) {
        emergencyContacts = source.phoneNumber;
    }

    @JsonValue
    public String getEmergencyContacts() {
        return emergencyContacts;
    }

    /**
     * Converts this Jackson-friendly adapted emergency contact object into the sugarmummy.recmfood.model's {@code
     * Phone} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted emergency contact.
     */
    public Phone toModelType() throws IllegalValueException {
        if (!Phone.isValidPhone(emergencyContacts)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(emergencyContacts);
    }

}

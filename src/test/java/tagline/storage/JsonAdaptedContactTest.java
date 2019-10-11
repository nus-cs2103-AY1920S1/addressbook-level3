package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalContacts.BENSON;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.contact.Address;
import tagline.model.contact.Email;
import tagline.model.contact.Name;
import tagline.model.contact.Phone;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();

    @Test
    public void toModelType_validContactDetails_returnsContact() throws Exception {
        JsonAdaptedContact contact = new JsonAdaptedContact(BENSON);
        assertEquals(BENSON, contact.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_PHONE,
                null, VALID_ADDRESS, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }
}

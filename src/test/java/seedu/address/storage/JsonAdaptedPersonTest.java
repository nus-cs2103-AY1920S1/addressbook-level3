package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.IRENE;
import static seedu.address.testutil.TypicalPersons.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Age;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_NRIC = "G123A";
    private static final String INVALID_TYPE = "student";
    private static final String INVALID_AGE = "-28";

    private static final String PLACEHOLDER_AGE = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_TYPE = BENSON.getType().toString();
    private static final String VALID_AGE = "28";

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(IRENE);
        assertEquals(IRENE, person.toModelType());
    }

    @Test
    public void toModelType_validDonorDetails_returnsDonor() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(JOHN);
        assertEquals(JOHN, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, INVALID_NAME, VALID_PHONE, PLACEHOLDER_AGE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, null, VALID_PHONE,
                PLACEHOLDER_AGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME, INVALID_PHONE, PLACEHOLDER_AGE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, VALID_NRIC, VALID_NAME, null,
                PLACEHOLDER_AGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_TYPE, INVALID_NRIC, VALID_NAME, INVALID_PHONE, PLACEHOLDER_AGE);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_TYPE, null, VALID_NAME, VALID_PHONE,
                PLACEHOLDER_AGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_TYPE, VALID_NRIC, VALID_NAME, INVALID_PHONE, PLACEHOLDER_AGE);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_NRIC, VALID_NAME, VALID_PHONE,
                PLACEHOLDER_AGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        String patientType = "patient";
        JsonAdaptedPerson patient = new JsonAdaptedPerson(patientType, VALID_NRIC, VALID_NAME, VALID_PHONE,
                INVALID_AGE);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        String patientType = "patient";
        JsonAdaptedPerson patient = new JsonAdaptedPerson(patientType, VALID_NRIC, VALID_NAME, VALID_PHONE,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }
}

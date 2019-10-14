package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.profile.person.Name;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_MEDICALHISTORY = "#high blood pressure";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DOB = BENSON.getDateOfBirth().toString();
    private static final String VALID_BLOODTYPE = BENSON.getBloodType().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_WEIGHT = BENSON.getWeight().toString();
    private static final String VALID_WEIGHT_TIMESTAMP = BENSON.getWeight().timestamp;
    private static final String VALID_HEIGHT = BENSON.getHeight().toString();
    private static final String VALID_HEIGHT_TIMESTAMP = BENSON.getHeight().timestamp;
    private static final List<JsonAdaptedMedicalHistory> VALID_MEDICALHISTORIES = BENSON.getMedicalHistories().stream()
            .map(JsonAdaptedMedicalHistory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_WEIGHT_TIMESTAMP, VALID_HEIGHT, VALID_HEIGHT_TIMESTAMP,
                        VALID_MEDICALHISTORIES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_WEIGHT_TIMESTAMP, VALID_HEIGHT, VALID_HEIGHT_TIMESTAMP,
                        VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}

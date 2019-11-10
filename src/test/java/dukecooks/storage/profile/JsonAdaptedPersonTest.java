package dukecooks.storage.profile;

import static dukecooks.testutil.profile.TypicalProfiles.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Weight;
import dukecooks.testutil.Assert;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_MEDICALHISTORY = "#high blood pressure";
    private static final String INVALID_BLOODTYPE = "1a";
    private static final String INVALID_DOB = "1a";
    private static final String INVALID_GENDER = "1a";
    private static final String INVALID_WEIGHT = "1a";
    private static final String INVALID_HEIGHT = "1a";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DOB = BENSON.getDateOfBirth().toString();
    private static final String VALID_BLOODTYPE = BENSON.getBloodType().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_WEIGHT = BENSON.getWeight().toString();
    private static final String VALID_HEIGHT = BENSON.getHeight().toString();
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
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMedicalHistories_throwsIllegalValueException() {
        List<JsonAdaptedMedicalHistory> invalidMedicalHistories = new ArrayList<>(VALID_MEDICALHISTORIES);
        invalidMedicalHistories.add(new JsonAdaptedMedicalHistory(INVALID_MEDICALHISTORY));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, invalidMedicalHistories);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidDoB_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullDoB_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                DoB.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, INVALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, null, VALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBloodType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, INVALID_BLOODTYPE,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, null,
                        VALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                BloodType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        INVALID_WEIGHT, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        null, VALID_HEIGHT, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Weight.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidHeight_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, INVALID_HEIGHT, VALID_MEDICALHISTORIES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullHeight_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_DOB, VALID_GENDER, VALID_BLOODTYPE,
                        VALID_WEIGHT, null, VALID_MEDICALHISTORIES);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Height.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}

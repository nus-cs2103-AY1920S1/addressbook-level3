package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Picture;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PICTURE = "+651234.xyz";
    private static final String INVALID_ATTENDANCE = "here";
    private static final String INVALID_CLASSID = " ";
    private static final String INVALID_RESULT = "hundred";
    private static final String INVALID_PARTICIPATION = "two";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PICTURE = BENSON.getPicture().toString();
    private static final String VALID_ATTENDANCE = BENSON.getAttendance().toString();
    private static final String VALID_CLASSID = BENSON.getClassId().toString();
    private static final String VALID_RESULT = BENSON.getResult().toString();
    private static final String VALID_PARTICIPATION = BENSON.getParticipation().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PICTURE, VALID_CLASSID,
                        VALID_ATTENDANCE, VALID_RESULT, VALID_PARTICIPATION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PICTURE, VALID_CLASSID,
                VALID_ATTENDANCE, VALID_RESULT, VALID_PARTICIPATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PICTURE, INVALID_CLASSID,
                        INVALID_ATTENDANCE, INVALID_RESULT, INVALID_PARTICIPATION);
        String expectedMessage = Picture.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}

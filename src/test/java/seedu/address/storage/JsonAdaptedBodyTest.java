package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static seedu.address.storage.JsonAdaptedBody.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.person.Name;

//@@author ambervoong
public class JsonAdaptedBodyTest {
    private static final String VALID_BODY_ID = "1";
    private static final String VALID_DATE_OF_ADMISSION = "11/11/1111";
    private static final String VALID_BODY_STATUS = "ARRIVED";
    private static final String VALID_NAME = "Rachel";

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_BODY_STATUS = "ARXIVED";
    public static final String INVALID_DATE = "17/ad/11";


    @Test
    public void toModelType_validBodyDetails_returnsBody() throws Exception {
        JsonAdaptedBody body = new JsonAdaptedBody(ALICE);
        assertEquals(ALICE, body.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBody body = new JsonAdaptedBody(VALID_BODY_ID, VALID_DATE_OF_ADMISSION, VALID_BODY_STATUS,
                INVALID_NAME, null, null, null, null, null, null,
                null, null, null, null, null);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, body::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedBody body = new JsonAdaptedBody(null, VALID_DATE_OF_ADMISSION, INVALID_BODY_STATUS,
                VALID_NAME, null, null, null, null, null, null,
                null, null, null, null, null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                IdentificationNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, body::toModelType);
    }

    @Test
    public void toModelType_illegalStatus_throwsIllegalValueException() {
        JsonAdaptedBody body = new JsonAdaptedBody(VALID_BODY_ID, VALID_DATE_OF_ADMISSION, null,
                VALID_NAME, null, null, null, null, null, null,
                null, null, null, null, null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BodyStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, body::toModelType);

        JsonAdaptedBody bodyWrongStatus = new JsonAdaptedBody(VALID_BODY_ID, VALID_DATE_OF_ADMISSION,
                INVALID_BODY_STATUS, VALID_NAME, null, null, null, null, null,
                null, null, null, null, null,
                null);

         expectedMessage = "Invalid Body Status: " + INVALID_BODY_STATUS.toLowerCase();
        assertThrows(ParseException.class, expectedMessage, bodyWrongStatus::toModelType);
    }

    @Test
    public void toModelType_invalidDates_throwsIllegalValueException() {
        JsonAdaptedBody bodyA = new JsonAdaptedBody(VALID_BODY_ID, VALID_DATE_OF_ADMISSION, VALID_BODY_STATUS,
                VALID_NAME, null, null, null, null, null, INVALID_DATE,
                null, null, null, null, null);
        JsonAdaptedBody bodyB = new JsonAdaptedBody(VALID_BODY_ID, VALID_DATE_OF_ADMISSION, VALID_BODY_STATUS,
                VALID_NAME, null, null, null, null, null, null,
                INVALID_DATE, null, null, null, null);
        JsonAdaptedBody bodyC = new JsonAdaptedBody(VALID_BODY_ID, INVALID_DATE, VALID_BODY_STATUS,
                VALID_NAME, null, null, null, null, null, null,
                null, null, null, null, null);
        assertThrows(ParseException.class, MESSAGE_INVALID_DATE, bodyA::toModelType);
        assertThrows(ParseException.class, MESSAGE_INVALID_DATE, bodyB::toModelType);
        assertThrows(ParseException.class, MESSAGE_INVALID_DATE, bodyC::toModelType);
    }
}

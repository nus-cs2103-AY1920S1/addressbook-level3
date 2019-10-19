package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

//@@author ambervoong
public class JsonAdaptedBodyTest {
    private static final String VALID_BODY_ID = "1";
    private static final String VALID_DATE_OF_ADMISSION = "11/11/1111";
    private static final String VALID_BODY_STATUS = "ARRIVED";

    private static final String INVALID_NAME = "R@chel";

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
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWorkers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

//@@author ambervoong
public class JsonAdaptedWorkerTest {
    private static final String VALID_WORKER_ID = "1";
    private static final String VALID_DATE_JOINED = "11/11/1111";
    private static final String VALID_NAME = "Arrete";
    private static final String VALID_SEX = "female";

    private static final String INVALID_NAME = "R@chel";

    @Test
    public void toModelType_validWorkerDetails_returnsWorker() throws Exception {
        JsonAdaptedWorker worker = new JsonAdaptedWorker(ALICE);
        assertEquals(ALICE, worker.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedWorker worker = new JsonAdaptedWorker(VALID_WORKER_ID, INVALID_NAME, VALID_SEX, VALID_DATE_JOINED,
                null, null, null, null);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, worker::toModelType);
    }
}

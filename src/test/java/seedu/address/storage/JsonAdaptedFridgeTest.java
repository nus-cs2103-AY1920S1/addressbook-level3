package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author ambervoong
public class JsonAdaptedFridgeTest {
    private static final String VALID_FRIDGE_ID = "1";
    private static final String VALID_BODY_ID = "2";
    private static final String VALID_FRIDGE_STATUS = "Occupied";

    /*
    @Test
    public void toModelType_validFridgeDetails_returnsFridge() throws Exception {
        Body body = new FridgeBuilder(ALICE_FRIDGE).build().getBody().get();
        JsonAdaptedFridge fridge = new JsonAdaptedFridge(ALICE_FRIDGE);
        Fridge newFridge = fridge.toModelType();
        newFridge.setBody(body);
        assertEquals(ALICE_FRIDGE, newFridge);
    }
    */

    @Test
    public void toModelType_invalidBodyId_throwsIllegalValueException() {
        JsonAdaptedFridge fridge = new JsonAdaptedFridge(VALID_FRIDGE_ID, "NNNO", VALID_BODY_ID);

        String expectedMessage = "NNNO" + " is not a valid FridgeStatus.";
        assertThrows(ParseException.class, expectedMessage, fridge::toModelType);
    }
}

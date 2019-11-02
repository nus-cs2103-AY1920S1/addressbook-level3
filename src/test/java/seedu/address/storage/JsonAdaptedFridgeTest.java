package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
public class JsonAdaptedFridgeTest {
    private static final String VALID_FRIDGE_ID = "1";
    private static final String VALID_BODY_ID = "2";
    private static final String VALID_FRIDGE_STATUS = "Occupied";
    private static final String INVALID_FRIDGE_STATUS = "NNNO";
    private static final String INVALID_FRIDGE_ID = "a";
    private static final String INVALID_BODY_ID = "b";

    @Test
    public void toModelType_validFridgeDetails_returnsFridge() throws Exception {
        Fridge fridge = Fridge.generateNewStoredFridge(1);
        fridge.setBody(null);
        JsonAdaptedFridge fridgeStorage = new JsonAdaptedFridge(fridge);
        Fridge newFridge = fridgeStorage.toModelType();

        // This is manually set here as toModelType does not update the Body field in Fridge.
        // The Body is only set in JsonSerializableAddressBook.
        newFridge.setBody(null);
        assertEquals(fridge, newFridge);
    }

    @Test
    public void toModelType_validFridgeWithBody_returnsFridge() throws Exception {
        Body body = new BodyBuilder().build();
        Fridge fridge = Fridge.generateNewStoredFridge(1);
        fridge.setBody(body);
        JsonAdaptedFridge fridgeStorage = new JsonAdaptedFridge(fridge);
        Fridge newFridge = fridgeStorage.toModelType();

        // This is manually set here as toModelType does not update the Body field in Fridge.
        // The Body is only set in JsonSerializableAddressBook.
        newFridge.setBody(body);
        assertEquals(fridge, newFridge);
    }

    @Test
    public void toModelType_invalidFridgeStatus_throwsIllegalValueException() {
        JsonAdaptedFridge fridge = new JsonAdaptedFridge(VALID_FRIDGE_ID, INVALID_FRIDGE_STATUS, VALID_BODY_ID);

        String expectedMessage = INVALID_FRIDGE_STATUS + " is not a valid FridgeStatus.";
        assertThrows(ParseException.class, expectedMessage, fridge::toModelType);
    }

    @Test
    public void toModelType_invalidFridgeId_throwsIllegalValueException() {
        JsonAdaptedFridge fridge = new JsonAdaptedFridge(INVALID_FRIDGE_ID, VALID_FRIDGE_STATUS, VALID_BODY_ID);
        assertThrows(IllegalValueException.class, MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX, fridge::toModelType);
    }

    @Test
    public void toModelType_invalidBodyId_throwsIllegalValueException() {
        JsonAdaptedFridge fridge = new JsonAdaptedFridge(VALID_FRIDGE_ID, VALID_FRIDGE_STATUS, INVALID_BODY_ID);
        assertThrows(IllegalValueException.class, MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX, fridge::toModelType);
    }
}

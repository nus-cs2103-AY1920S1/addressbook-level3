package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.inventory.Name;

class JsonAdaptedInventoryTest {

    private static final int INT_VALUE = 100;
    private static final boolean BOOLEAN_VALUE = true;

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedInventory jsonAdaptedInventory = new JsonAdaptedInventory("", BOOLEAN_VALUE, INT_VALUE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;

        try {
            jsonAdaptedInventory.toModelType();

            assertTrue(false);

        } catch (IllegalValueException e) {

            if (e.getMessage().equals(expectedMessage)) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }

        }

    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedInventory jsonAdaptedInventory = new JsonAdaptedInventory(null, BOOLEAN_VALUE, INT_VALUE);
        String expectedMessage = String.format(JsonAdaptedInventory.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());


        try {
            jsonAdaptedInventory.toModelType();

            assertTrue(false);

        } catch (IllegalValueException e) {

            if (e.getMessage().equals(expectedMessage)) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }

        }

    }

}

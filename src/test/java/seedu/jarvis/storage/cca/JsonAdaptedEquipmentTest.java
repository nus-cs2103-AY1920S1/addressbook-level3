package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalEquipments.BOAT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.Equipment;

/**
 * Tests the behaviour of {@code JsonAdaptedEquipment}.
 */
public class JsonAdaptedEquipmentTest {
    private static final Equipment VALID_EQUIPMENT = BOAT;

    @Test
    public void toModelType_validEquipmentName_returnsEquipment() throws Exception {
        JsonAdaptedEquipment jsonAdaptedEquipment = new JsonAdaptedEquipment(VALID_EQUIPMENT);
        assertEquals(VALID_EQUIPMENT, jsonAdaptedEquipment.toModelType());
    }

    @Test
    public void toModelType_invalidEquipmentName_throwsIllegalValueException() {
        String invalidEquipmentName = "*";
        JsonAdaptedEquipment jsonAdaptedEquipment = new JsonAdaptedEquipment(invalidEquipmentName);
        assertThrows(IllegalValueException.class, Equipment.MESSAGE_CONSTRAINTS, jsonAdaptedEquipment::toModelType);
    }
}

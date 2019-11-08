package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.person.Slot;

class JsonAdaptedSlotTest {

    @Test
    public void getSlot_validSlotString_success() {
        JsonAdaptedSlot slotAmy = new JsonAdaptedSlot(VALID_SLOT_AMY);
        JsonAdaptedSlot slotBob = new JsonAdaptedSlot(VALID_SLOT_BOB);
        assertEquals(VALID_SLOT_AMY, slotAmy.getSlot());
        assertEquals(VALID_SLOT_BOB, slotBob.getSlot());
    }

    @Test
    public void getSlot_validSlotObject_success() {
        Slot slotAmy = Slot.fromString(VALID_SLOT_AMY);
        Slot slotBob = Slot.fromString(VALID_SLOT_BOB);
        JsonAdaptedSlot jsonSlotAmy = new JsonAdaptedSlot(slotAmy);
        JsonAdaptedSlot jsonSlotBob = new JsonAdaptedSlot(slotBob);
        assertEquals(VALID_SLOT_AMY, jsonSlotAmy.getSlot());
        assertEquals(VALID_SLOT_BOB, jsonSlotBob.getSlot());
    }

    @Test
    public void toModelType_validSlot_success() throws Exception {
        JsonAdaptedSlot slotAmy = new JsonAdaptedSlot(VALID_SLOT_AMY);
        JsonAdaptedSlot slotBob = new JsonAdaptedSlot(VALID_SLOT_BOB);
        assertEquals(Slot.fromString(VALID_SLOT_AMY), slotAmy.toModelType());
        assertEquals(Slot.fromString(VALID_SLOT_BOB), slotBob.toModelType());
    }

    @Test
    public void toModelType_invalidSlot_throwsIllegalValueException() {
        JsonAdaptedSlot slot = new JsonAdaptedSlot("    ");
        assertThrows(IllegalArgumentException.class, Slot.MESSAGE_CONSTRAINTS, slot::toModelType);
    }
}

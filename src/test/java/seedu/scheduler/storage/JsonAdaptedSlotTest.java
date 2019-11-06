package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.person.Slot;

class JsonAdaptedSlotTest {

    @Test
    public void toModelType_validSlotDetails_success() throws Exception {
        JsonAdaptedSlot slot = new JsonAdaptedSlot(VALID_SLOT_AMY);
        assertEquals(Slot.fromString(VALID_SLOT_AMY), slot.toModelType());
    }
}

package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.cca.TypicalEquipments.PADDLE;

import org.junit.jupiter.api.Test;

public class EquipmentListTest {

    private final EquipmentList equipmentList = new EquipmentList();

    @Test
    public void contains_equipmentNotInList_returnsFalse() {
        assertFalse(equipmentList.contains(PADDLE));
    }

    @Test
    public void contains_equipmentInList_returnsTrue() {
        equipmentList.addEquipment(PADDLE);
        assertTrue(equipmentList.contains(PADDLE));
    }
}

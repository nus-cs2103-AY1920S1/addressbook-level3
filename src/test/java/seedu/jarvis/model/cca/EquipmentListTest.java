package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalEquipments.PADDLE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.exceptions.DuplicateEquipmentException;
import seedu.jarvis.model.cca.exceptions.EquipmentNotFoundException;

public class EquipmentListTest {

    private final EquipmentList equipmentList = new EquipmentList();

    @Test
    public void contains_equipmentNotInList_returnsFalse() {
        assertFalse(equipmentList.contains(PADDLE));
    }

    @Test
    public void contains_nullEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> equipmentList.contains(null));
    }

    @Test
    public void contains_equipmentInList_returnsTrue() {
        equipmentList.addEquipment(PADDLE);
        assertTrue(equipmentList.contains(PADDLE));
    }


    @Test
    public void add_nullEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> equipmentList.addEquipment(null));
    }

    @Test
    public void add_duplicateEquipment_throwsDuplicateEquipmentException() {
        equipmentList.addEquipment(PADDLE);
        assertThrows(DuplicateEquipmentException.class, () -> equipmentList.addEquipment(PADDLE));
    }

    @Test
    public void setPerson_nullTargetEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> equipmentList.updateEquipment(null, PADDLE));
    }

    @Test
    public void setEquipment_nullEditedEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> equipmentList.updateEquipment(PADDLE, null));
    }

    @Test
    public void setEquipment_targetEquipmentNotInList_throwsEquipmentNotFoundException() {
        assertThrows(EquipmentNotFoundException.class, () -> equipmentList.updateEquipment(PADDLE, PADDLE));
    }
}

package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.EditCcaCommand.EditCcaDescriptor;

/**
 * Tests the behaviour of {@code EditCcaDescriptorTest}.
 */
public class JsonAdaptedEditCcaDescriptorTest {

    @Test
    public void toModelType_validDescriptor_returnsEditCcaDescriptor() throws Exception {
        EditCcaDescriptor editCcaDescriptor = new EditCcaDescriptor();
        editCcaDescriptor.setCcaName(CANOEING.getName());
        editCcaDescriptor.setCcaType(CANOEING.getCcaType());
        editCcaDescriptor.setEquipmentList(CANOEING.getEquipmentList());
        editCcaDescriptor.setCcaProgress(CANOEING.getCcaProgress());
        JsonAdaptedEditCcaDescriptor jsonAdaptedEditCcaDescriptor = new JsonAdaptedEditCcaDescriptor(editCcaDescriptor);
        assertEquals(editCcaDescriptor, jsonAdaptedEditCcaDescriptor.toModelType());
    }
}

package seedu.jarvis.storage.history.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;
import static seedu.jarvis.testutil.cca.TypicalCcas.GUITAR_ENSEMBLE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.EditCcaCommand;
import seedu.jarvis.logic.commands.cca.EditCcaCommand.EditCcaDescriptor;

/**
 * Tests the behaviour of {@code JsonAdaptedEditCcaCommand}.
 */
public class JsonAdaptedEditCcaCommandTest {

    @Test
    public void toModelType_validIndexValidDescriptorValidOriginalCcaValidEditedCca_returnsEditCcaCommand()
            throws Exception {
        EditCcaCommand editCcaCommand = new EditCcaCommand(INDEX_FIRST_CCA, new EditCcaDescriptor(), CANOEING,
                GUITAR_ENSEMBLE);
        JsonAdaptedEditCcaCommand jsonAdaptedEditCcaCommand = new JsonAdaptedEditCcaCommand(editCcaCommand);
        assertEquals(editCcaCommand, jsonAdaptedEditCcaCommand.toModelType());
    }

    @Test
    public void toModelType_validIndexValidDescriptorNullOriginalCcaNullEditedCca_returnsEditCcaCommand()
            throws Exception {
        EditCcaCommand editCcaCommand = new EditCcaCommand(INDEX_FIRST_CCA, new EditCcaDescriptor());
        JsonAdaptedEditCcaCommand jsonAdaptedEditCcaCommand = new JsonAdaptedEditCcaCommand(editCcaCommand);
        assertEquals(editCcaCommand, jsonAdaptedEditCcaCommand.toModelType());
    }
}

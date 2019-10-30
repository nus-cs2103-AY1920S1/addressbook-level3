package seedu.jarvis.storage.history.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.DeleteCcaCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedDeleteCcaCommand}.
 */
public class JsonAdaptedDeleteCcaCommandTest {

    @Test
    public void toModelType_validIndexNonNullCca_returnsDeleteCcaCommand() throws Exception {
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(INDEX_FIRST_CCA, CANOEING);
        JsonAdaptedDeleteCcaCommand jsonAdaptedDeleteCcaCommand = new JsonAdaptedDeleteCcaCommand(deleteCcaCommand);
        assertEquals(deleteCcaCommand, jsonAdaptedDeleteCcaCommand.toModelType());
    }

    @Test
    public void toModelType_validIndexNullCca_returnsDeleteCcaCommand() throws Exception {
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(INDEX_FIRST_CCA);
        JsonAdaptedDeleteCcaCommand jsonAdaptedDeleteCcaCommand = new JsonAdaptedDeleteCcaCommand(deleteCcaCommand);
        assertEquals(deleteCcaCommand, jsonAdaptedDeleteCcaCommand.toModelType());
    }
}

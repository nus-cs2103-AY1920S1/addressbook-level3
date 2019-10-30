package seedu.jarvis.storage.history.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.AddCcaCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedAddCcaCommand}.
 */
public class JsonAdaptedAddCcaCommandTest {

    @Test
    public void toModelType_addCca_returnsAddCcaCommand() throws Exception {
        AddCcaCommand addCcaCommand = new AddCcaCommand(CANOEING);
        JsonAdaptedAddCcaCommand jsonAdaptedAddCcaCommand = new JsonAdaptedAddCcaCommand(addCcaCommand);
        assertEquals(addCcaCommand, jsonAdaptedAddCcaCommand.toModelType());
    }
}

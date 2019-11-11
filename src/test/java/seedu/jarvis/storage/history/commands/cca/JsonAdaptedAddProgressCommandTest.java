package seedu.jarvis.storage.history.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.AddProgressCommand;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;

/**
 * Tests the behaviour {@code JsonAdaptedAddProgressCommand}.
 */
public class JsonAdaptedAddProgressCommandTest {

    @Test
    public void toModelType_addProgress_returnsAddProgressCommand() throws Exception {
        AddProgressCommand addProgressCommand = new AddProgressCommand(INDEX_FIRST_CCA, new CcaMilestoneList(),
                CANOEING);
        JsonAdaptedAddProgressCommand jsonAdaptedAddProgressCommand = new JsonAdaptedAddProgressCommand(
                addProgressCommand);
        assertEquals(addProgressCommand, jsonAdaptedAddProgressCommand.toModelType());
    }
}

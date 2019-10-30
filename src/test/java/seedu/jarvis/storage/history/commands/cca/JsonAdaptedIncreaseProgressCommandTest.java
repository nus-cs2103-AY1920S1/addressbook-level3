package seedu.jarvis.storage.history.commands.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;
import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.IncreaseProgressCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedIncreaseProgressCommand}.
 */
public class JsonAdaptedIncreaseProgressCommandTest {

    @Test
    public void toModelType_validIndexNonNullCca_returnsIncreaseProgressCommand() throws Exception {
        IncreaseProgressCommand increaseProgressCommand = new IncreaseProgressCommand(INDEX_FIRST_CCA, CANOEING);
        JsonAdaptedIncreaseProgressCommand jsonAdaptedIncreaseProgressCommand = new JsonAdaptedIncreaseProgressCommand(
                increaseProgressCommand);
        assertEquals(increaseProgressCommand, jsonAdaptedIncreaseProgressCommand.toModelType());
    }

    @Test
    public void toModelType_validIndexNullCca_returnsIncreaseProgressCommand() throws Exception {
        IncreaseProgressCommand increaseProgressCommand = new IncreaseProgressCommand(INDEX_FIRST_CCA);
        JsonAdaptedIncreaseProgressCommand jsonAdaptedIncreaseProgressCommand = new JsonAdaptedIncreaseProgressCommand(
                increaseProgressCommand);
        assertEquals(increaseProgressCommand, jsonAdaptedIncreaseProgressCommand.toModelType());
    }

}

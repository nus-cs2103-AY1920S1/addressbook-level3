package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_PURCHASE;
import static seedu.jarvis.testutil.finance.TypicalPurchases.DINNER_REEDZ;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.RemovePaidCommand;

/**
 * Tests the behaviour of {@code RemovePaidCommand}.
 */
public class JsonAdaptedRemovePaidCommandTest {

    @Test
    public void toModelType_validIndexNonNullPurchase_returnsRemovePaidCommand() throws Exception {
        RemovePaidCommand removePaidCommand = new RemovePaidCommand(INDEX_SECOND_PURCHASE, DINNER_REEDZ);
        JsonAdaptedRemovePaidCommand jsonAdaptedRemovePaidCommand = new JsonAdaptedRemovePaidCommand(removePaidCommand);
        assertEquals(removePaidCommand, jsonAdaptedRemovePaidCommand.toModelType());
    }

    @Test
    public void toModelType_validIndexNullPurchase_returnsRemovePaidCommand() throws Exception {
        RemovePaidCommand removePaidCommand = new RemovePaidCommand(INDEX_SECOND_PURCHASE);
        JsonAdaptedRemovePaidCommand jsonAdaptedRemovePaidCommand = new JsonAdaptedRemovePaidCommand(removePaidCommand);
        assertEquals(removePaidCommand, jsonAdaptedRemovePaidCommand.toModelType());
    }
}

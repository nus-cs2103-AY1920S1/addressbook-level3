package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.finance.TypicalPurchases.DINNER_REEDZ;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetPaidCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedSetPaidCommand}.
 */
public class JsonAdaptedSetPaidCommandTest {

    @Test
    public void toModelType_returnsSetPaidCommand() throws Exception {
        SetPaidCommand setPaidCommand = new SetPaidCommand(DINNER_REEDZ);
        JsonAdaptedSetPaidCommand jsonAdaptedSetPaidCommand = new JsonAdaptedSetPaidCommand(setPaidCommand);
        assertEquals(setPaidCommand, jsonAdaptedSetPaidCommand.toModelType());
    }
}

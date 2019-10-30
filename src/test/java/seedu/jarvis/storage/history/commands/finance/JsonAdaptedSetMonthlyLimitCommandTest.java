package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetMonthlyLimitCommand;
import seedu.jarvis.model.finance.MonthlyLimit;

/**
 * Tests the behaviour of {@code SetMonthlyLimitCommand}.
 */
public class JsonAdaptedSetMonthlyLimitCommandTest {

    @Test
    public void toModelType_nonNullOriginalLimit_returnsSetMonthlyLimitCommand() throws Exception {
        SetMonthlyLimitCommand setMonthlyLimitCommand = new SetMonthlyLimitCommand(new MonthlyLimit("20"),
                new MonthlyLimit("10"));
        JsonAdaptedSetMonthlyLimitCommand jsonAdaptedSetMonthlyLimitCommand = new JsonAdaptedSetMonthlyLimitCommand(
                setMonthlyLimitCommand);
        assertEquals(setMonthlyLimitCommand, jsonAdaptedSetMonthlyLimitCommand.toModelType());
    }

    @Test
    public void toModelType_nullOriginalLimit_returnsSetMonthlyLimitCommand() throws Exception {
        SetMonthlyLimitCommand setMonthlyLimitCommand = new SetMonthlyLimitCommand(new MonthlyLimit("20"), null);
        JsonAdaptedSetMonthlyLimitCommand jsonAdaptedSetMonthlyLimitCommand = new JsonAdaptedSetMonthlyLimitCommand(
                setMonthlyLimitCommand);
        assertEquals(setMonthlyLimitCommand, jsonAdaptedSetMonthlyLimitCommand.toModelType());
    }

}

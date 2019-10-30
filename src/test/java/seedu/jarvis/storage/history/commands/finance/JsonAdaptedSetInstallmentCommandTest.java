package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.finance.TypicalInstallments.PHONE_BILL;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetInstallmentCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedSetInstallmentCommand}.
 */
public class JsonAdaptedSetInstallmentCommandTest {

    @Test
    public void toModelType_addInstallment_returnsSetInstallmentCommand() throws Exception {
        SetInstallmentCommand setInstallmentCommand = new SetInstallmentCommand(PHONE_BILL);
        JsonAdaptedSetInstallmentCommand jsonAdaptedSetInstallmentCommand = new JsonAdaptedSetInstallmentCommand(
                setInstallmentCommand);
        assertEquals(setInstallmentCommand, jsonAdaptedSetInstallmentCommand.toModelType());
    }
}

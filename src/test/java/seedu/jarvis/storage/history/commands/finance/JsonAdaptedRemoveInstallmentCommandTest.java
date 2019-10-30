package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.finance.TypicalInstallments.PHONE_BILL;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.RemoveInstallmentCommand;

/**
 * Tests the behaviour of {@code RemoveInstallmentCommand}.
 */
public class JsonAdaptedRemoveInstallmentCommandTest {

    @Test
    public void toModelType_validIndexNonNullInstallment_returnsRemoveInstallmentCommand() throws Exception {
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT,
                PHONE_BILL);
        JsonAdaptedRemoveInstallmentCommand jsonAdaptedRemoveInstallmentCommand =
                new JsonAdaptedRemoveInstallmentCommand(removeInstallmentCommand);
        assertEquals(removeInstallmentCommand, jsonAdaptedRemoveInstallmentCommand.toModelType());
    }

    @Test
    public void toModelType_validIndexNullInstallment_returnsRemoveInstallmentCommand() throws Exception {
        RemoveInstallmentCommand removeInstallmentCommand = new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT);
        JsonAdaptedRemoveInstallmentCommand jsonAdaptedRemoveInstallmentCommand =
                new JsonAdaptedRemoveInstallmentCommand(removeInstallmentCommand);
        assertEquals(removeInstallmentCommand, jsonAdaptedRemoveInstallmentCommand.toModelType());
    }
}

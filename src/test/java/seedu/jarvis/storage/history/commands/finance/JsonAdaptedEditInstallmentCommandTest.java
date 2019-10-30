package seedu.jarvis.storage.history.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;
import static seedu.jarvis.testutil.finance.TypicalInstallments.PHONE_BILL;
import static seedu.jarvis.testutil.finance.TypicalInstallments.TRANSPORT_CONCESSION;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedEditInstallmentCommand}.
 */
public class JsonAdaptedEditInstallmentCommandTest {
    private static final Index VALID_INDEX = INDEX_FIRST_INSTALLMENT;
    private static final EditInstallmentDescriptor VALID_DESCRIPTOR = new EditInstallmentDescriptorBuilder()
            .withDescription(TRANSPORT_CONCESSION.getDescription().installmentDescription)
            .withSubscriptionFee(TRANSPORT_CONCESSION.getMoneySpentOnInstallment().toString())
            .build();
    private static final Installment ORIGINAL_INSTALLMENT = PHONE_BILL;
    private static final Installment EDITED_INSTALLMENT = TRANSPORT_CONCESSION;

    @Test
    public void toModelType_validIndexValidDescriptorWithInstallments_returnsEditInstallmentCommand() throws Exception {
        EditInstallmentCommand editInstallmentCommand = new EditInstallmentCommand(VALID_INDEX, VALID_DESCRIPTOR,
                ORIGINAL_INSTALLMENT, EDITED_INSTALLMENT);
        JsonAdaptedEditInstallmentCommand jsonAdaptedEditInstallmentCommand = new JsonAdaptedEditInstallmentCommand(
                editInstallmentCommand);
        assertEquals(editInstallmentCommand, jsonAdaptedEditInstallmentCommand.toModelType());
    }
}

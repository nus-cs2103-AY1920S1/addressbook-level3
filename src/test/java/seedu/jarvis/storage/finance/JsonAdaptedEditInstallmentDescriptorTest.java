package seedu.jarvis.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.finance.InstallmentBuilder.DEFAULT_DESCRIPTION;
import static seedu.jarvis.testutil.finance.InstallmentBuilder.DEFAULT_MONEY;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;

/**
 * Tests the behaviour of {@code JsonAdaptedEditInstallmentDescriptor}.
 */
public class JsonAdaptedEditInstallmentDescriptorTest {
    private static final String VALID_DESCRIPTION = DEFAULT_DESCRIPTION.installmentDescription;
    private static final String VALID_AMOUNT = DEFAULT_MONEY.toString();

    @Test
    public void toModelType() throws Exception {
        EditInstallmentDescriptor descriptor = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION)
                .withSubscriptionFee(VALID_AMOUNT)
                .build();
        JsonAdaptedEditInstallmentDescriptor jsonAdaptedEditInstallmentDescriptor =
                new JsonAdaptedEditInstallmentDescriptor(descriptor);
        assertEquals(descriptor, jsonAdaptedEditInstallmentDescriptor.toModelType());
    }
}

package seedu.jarvis.testutil.finance;

import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

/**
 * A utility class to help with building EditInstallmentDescriptor objects.
 */
public class EditInstallmentDescriptorBuilder {

    private EditInstallmentCommand.EditInstallmentDescriptor descriptor;

    public EditInstallmentDescriptorBuilder() {
        descriptor = new EditInstallmentCommand.EditInstallmentDescriptor();
    }

    public EditInstallmentDescriptorBuilder(EditInstallmentCommand.EditInstallmentDescriptor descriptor) {
        this.descriptor = new EditInstallmentCommand.EditInstallmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditInstallmentDescriptor} with fields containing details of {@code installment}
     */
    public EditInstallmentDescriptorBuilder(Installment installment) {
        descriptor = new EditInstallmentCommand.EditInstallmentDescriptor();
        descriptor.setDescription(installment.getDescription());
        descriptor.setMoneyPaid(installment.getMoneySpentOnInstallment());
    }

    /**
     * Sets the {@code InstallmentDescription} of the {@code EditInstallmentDescriptor} that we are building.
     */
    public EditInstallmentDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new InstallmentDescription(description));
        return this;
    }

    /**
     * Sets the {@code InstallmentMoneyPaid} of the {@code EditInstallmentDescriptor} that we are building.
     */
    public EditInstallmentDescriptorBuilder withSubscriptionFee(String money) {
        descriptor.setMoneyPaid(new InstallmentMoneyPaid(money));
        return this;
    }

    public EditInstallmentCommand.EditInstallmentDescriptor build() {
        return descriptor;
    }
}

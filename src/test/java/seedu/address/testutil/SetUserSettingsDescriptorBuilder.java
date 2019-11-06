package seedu.address.testutil;

import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.SetCommand;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.MaxRenews;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * A utility class to help with building SetUserSettingsDescriptor objects.
 */
public class SetUserSettingsDescriptorBuilder {
    private SetCommand.SetUserSettingsDescriptor descriptor;

    public SetUserSettingsDescriptorBuilder() {
        descriptor = new SetCommand.SetUserSettingsDescriptor();
    }

    /**
     * Returns an {@code SetUserSettingsDescriptor} with fields containing {@code UserSettings}'s details
     */
    public SetUserSettingsDescriptorBuilder(UserSettings userSettings) {
        descriptor = new SetCommand.SetUserSettingsDescriptor();
        descriptor.setLoanPeriod(new LoanPeriod(userSettings.getLoanPeriod()));
        descriptor.setRenewPeriod(new RenewPeriod(userSettings.getRenewPeriod()));
        descriptor.setFineIncrement(new FineIncrement(userSettings.getFineIncrement()));
        descriptor.setMaxRenews(new MaxRenews(userSettings.getMaxRenews()));
    }

    /**
     * Sets the {@code LoanPeriod} of the {@code SetUserSettingsDescriptor} that we are building.
     */
    public SetUserSettingsDescriptorBuilder withLoanPeriod(String loanPeriod) {
        descriptor.setLoanPeriod(new LoanPeriod(loanPeriod));
        return this;
    }

    /**
     * Sets the {@code RenewPeriod} of the {@code SetUserSettingsDescriptor} that we are building.
     */
    public SetUserSettingsDescriptorBuilder withRenewPeriod(String renewPeriod) {
        descriptor.setRenewPeriod(new RenewPeriod(renewPeriod));
        return this;
    }

    /**
     * Sets the {@code FineIncrement} of the {@code SetUserSettingsDescriptor} that we are building.
     */
    public SetUserSettingsDescriptorBuilder withFineIncrement(String fineIncrement) {
        descriptor.setFineIncrement(new FineIncrement(fineIncrement));
        return this;
    }

    /**
     * Sets the {@code MaxRenews} of the {@code SetUserSettingsDescriptor} that we are building.
     */
    public SetUserSettingsDescriptorBuilder withMaxRenews(String maxRenews) {
        descriptor.setMaxRenews(new MaxRenews(maxRenews));
        return this;
    }

    public SetCommand.SetUserSettingsDescriptor build() {
        return descriptor;
    }
}

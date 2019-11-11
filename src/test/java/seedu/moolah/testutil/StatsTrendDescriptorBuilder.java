package seedu.moolah.testutil;

import seedu.moolah.logic.commands.statistics.StatsTrendDescriptor;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.statistics.Mode;

/**
 * A utility class to help with building StatsTrendDescriptor objects.
 */
public class StatsTrendDescriptorBuilder {

    private StatsTrendDescriptor descriptor;

    public StatsTrendDescriptorBuilder() {
        descriptor = new StatsTrendDescriptor();
    }

    public StatsTrendDescriptorBuilder(StatsTrendDescriptor descriptor) {
        this.descriptor = new StatsTrendDescriptor(descriptor);
    }

    /**
     * Sets the {@code String} of the {@code StatsTrendDescriptor} that we are building.
     */
    public StatsTrendDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(Timestamp.createTimestampIfValid(startDate).get());
        return this;
    }

    /**
     * Sets the {@code String} of the {@code StatsDescriptor} that we are building.
     */
    public StatsTrendDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(Timestamp.createTimestampIfValid(endDate).get());
        return this;
    }

    /**
     * Sets the {@code String} of the {@code StatsDescriptor} that we are building.
     */
    public StatsTrendDescriptorBuilder withMode(String mode) {
        descriptor.setMode(new Mode(mode).isBudgetMode());
        return this;
    }

    public StatsTrendDescriptor build() {
        return descriptor;
    }

}


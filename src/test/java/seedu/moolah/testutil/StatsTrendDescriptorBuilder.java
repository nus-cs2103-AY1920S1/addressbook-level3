package seedu.moolah.testutil;

import seedu.moolah.logic.commands.statistics.StatsTrendDescriptor;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.statistics.Mode;
import seedu.moolah.model.statistics.TrendStatistics;

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

    //dk if its right to make it biased because a TabularStatistics dont follow the same signature
    /**
     * Returns an {@code StatsTrendDescriptor} with fields containing {@code statistics}'s details
     */
    public StatsTrendDescriptorBuilder(TrendStatistics statistics) {
        descriptor = new StatsTrendDescriptor();
        descriptor.setStartDate(statistics.getStartDate());
        descriptor.setEndDate(statistics.getEndDate());
        descriptor.setMode(statistics.isBudgetLimitMode());
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

    //not sure if I want to change this to enum soon, for now just put the object
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


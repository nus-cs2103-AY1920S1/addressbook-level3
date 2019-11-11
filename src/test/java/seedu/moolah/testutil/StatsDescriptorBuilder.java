package seedu.moolah.testutil;

import seedu.moolah.logic.commands.statistics.StatsDescriptor;
import seedu.moolah.model.general.Timestamp;


/**
 * A utility class to help with building StatsDescriptor objects.
 */
public class StatsDescriptorBuilder {

    private StatsDescriptor descriptor;

    public StatsDescriptorBuilder() {
        descriptor = new StatsDescriptor();
    }

    public StatsDescriptorBuilder(StatsDescriptor descriptor) {
        this.descriptor = new StatsDescriptor(descriptor);
    }

    /**
     * Sets the {@code String} of the {@code StatsDescriptor} that we are building.
     */
    public StatsDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(Timestamp.createTimestampIfValid(startDate).get());
        return this;
    }

    /**
     * Sets the {@code String} of the {@code StatsDescriptor} that we are building.
     */
    public StatsDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(Timestamp.createTimestampIfValid(endDate).get());
        return this;
    }

    public StatsDescriptor build() {
        return descriptor;
    }

}


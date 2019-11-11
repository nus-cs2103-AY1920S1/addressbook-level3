package seedu.moolah.testutil;

import seedu.moolah.logic.commands.statistics.StatsDescriptor;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.statistics.PieChartStatistics;


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

    //dk if its right to make it biased because a TabularStatistics dont follow the same signature
    /**
     * Returns an {@code StatsDescriptor} with fields containing {@code statistics}'s details
     */
    public StatsDescriptorBuilder(PieChartStatistics statistics) {
        descriptor = new StatsDescriptor();
        descriptor.setStartDate(statistics.getStartDate());
        descriptor.setEndDate(statistics.getEndDate());
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


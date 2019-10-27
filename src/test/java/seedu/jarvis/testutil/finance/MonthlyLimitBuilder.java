package seedu.jarvis.testutil.finance;

import seedu.jarvis.model.finance.MonthlyLimit;

/**
 * A utility class to help with building MonthyLimit objects.
 */
public class MonthlyLimitBuilder {

    public static final String DEFAULT_LIMIT = "800.0";

    private String limit;

    public MonthlyLimitBuilder() {
        limit = DEFAULT_LIMIT;
    }

    /**
     * Initialises the MonthlyLimitBuilder with the data of {@code toCopy}
     */
    public MonthlyLimitBuilder(MonthlyLimit toCopy) {
        limit = Double.toString(toCopy.getMonthlyLimit());
    }

    /**
     * Sets the limit of the {@code MonthlyLimit} that we are building.
     */
    public MonthlyLimitBuilder withLimit(String limitSet) {
        this.limit = limitSet;
        return this;
    }

    public MonthlyLimit build() {
        return new MonthlyLimit(limit);
    }
}

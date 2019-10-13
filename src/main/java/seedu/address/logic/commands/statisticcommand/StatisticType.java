package seedu.address.logic.commands.statisticcommand;

/**
 * To differentiate which type of statistic to calculate
 */
public enum StatisticType {
    PROFIT("PROFIT"),
    REVENUE("REVENUE"),
    COST("COST"),
    DEFAULT_COST("DEFAULT COST"),
    DEFAULT_PROFIT("DEFAULT PROFIT"),
    DEFAULT_REVENUE("DEFAULT REVENUE");

    private final String type;

    StatisticType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

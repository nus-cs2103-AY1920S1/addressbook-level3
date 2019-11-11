package seedu.address.logic.commands.statisticcommand;

/**
 * To differentiate which type of statistic to calculate
 */
public enum StatisticType {
    PROFIT("profit"),
    REVENUE("revenue"),
    COST("cost");

    private final String type;

    StatisticType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}

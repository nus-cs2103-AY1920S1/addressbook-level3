package seedu.address.logic.commands.statisticcommand;

/**
 * To differentiate which type of statistic to calculate
 */
public enum StatisticType {
    PROFIT("PROFIT"),
    REVENUE("REVENUE"),
    COST("COST");

    private final String type;

    StatisticType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}

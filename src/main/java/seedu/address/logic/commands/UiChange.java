package seedu.address.logic.commands;

/**
 * represent the type of commandResult that is executed
 * so to enable the manager to switch to the correct tab
 */
public enum UiChange {
    DEFAULT,
    CUSTOMER,
    PHONE,
    ORDER,
    SCHEDULE,
    STATS_TOTAL_PROFIT_ON_COMPLETED,
    STATS_TOTAL_REVENUE_ON_COMPLETED,
    STATS_TOTAL_COST_ON_COMPLETED,
    HELP,
    EXIT;
}

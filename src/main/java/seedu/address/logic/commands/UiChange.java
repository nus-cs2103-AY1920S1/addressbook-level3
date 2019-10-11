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
    STATS_TOTAL_PROFIT,
    STATS_TOTAL_REVENUE,
    STATS_TOTAL_COST,
    HELP,
    EXIT;
}

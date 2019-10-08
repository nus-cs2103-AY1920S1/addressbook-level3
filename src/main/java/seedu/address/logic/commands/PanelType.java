package seedu.address.logic.commands;

/**
 * represent the type of commandResult that is executed
 * so to enable the manager to switch to the correct tab
 */
public enum PanelType {
    DEFAULT,
    CUSTOMER,
    PHONE,
    ORDER,
    SCHEDULE;
}

package seedu.guilttrip.ui.util;

import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Represents a PanelName.
 */
public enum PanelName {


    MAIN, BUDGET, WISH, REMINDER, AUTOEXPENSE;

    public static final String MESSAGE_CONSTRAINTS = "Panel names should be one of the following: wishlist,"
            + "budget(s), reminder(s) or autoexpense(s)\n"
            + "aliases: w, b, r, ae";

    /**
     * Parses input {@code panelNameString} and returns {@code PanelName} that is standardised.
     * @throws ParseException if input string is not a recognised PanelName
     */
    public static PanelName parse(String panelNameString) throws ParseException {


        switch (panelNameString.toLowerCase().trim()) {
        case "wishlist":
            // Fallthrough
        case "wishes":
            // Fallthrough
        case "wish":
            // Fallthrough
        case "w":
            return WISH;
        case "budget":
            // Fallthrough
        case "budgets":
            // Fallthrough
        case "b":
            return BUDGET;
        case "reminders":
            // Fallthrough
        case "generalReminder":
            // Fallthrough
        case "r":
            return REMINDER;
        case "autoexpense":
            // Fallthrough
        case "autoexpenses":
            // Fallthrough
        case "autoexp":
            // Fallthrough
        case "ae":
            return AUTOEXPENSE;
        default:
            throw new ParseException("Invalid panel name");
        }

    }

    public static String toString(PanelName panelName) {
        return panelName + "";
    }
}

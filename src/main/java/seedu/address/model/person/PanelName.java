package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a PanelName.
 */
public class PanelName {

    public static final String MESSAGE_CONSTRAINTS = "Panel names should be one of the following: wishlist, budget or "
            + "reminder";

    public final String panelName;

    /**
     * Construct a {@code PanelName} with the specified field.
     */
    public PanelName(String panelName) {
        requireAllNonNull(panelName);
        checkArgument(isValidPanelName(panelName), MESSAGE_CONSTRAINTS);
        this.panelName = panelName;
    }

    public String getName() {
        return this.panelName;
    }

    /**
     * Returns true if a given string is a valid panel name.
     */
    public static boolean isValidPanelName(String test) {
        String testLowerCase = test.toLowerCase();

        switch (testLowerCase) {
        case "wishlist":
            // Fallthrough
        case "wishes":
            // Fallthrough
        case "wish":
            // Fallthrough
        case "w":
            // Fallthrough
        case "budget":
            // Fallthrough
        case "budgets":
            // Fallthrough
        case "b":
            // Fallthrough
        case "reminders":
            // Fallthrough
        case "reminder":
            // Fallthrough
        case "r":
            return true; // all the above cases are accepted arguments
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PanelName // instanceof handles nulls
                && panelName.equals(((PanelName) other).panelName)); // state check
    }

    @Override
    public int hashCode() {
        return panelName.hashCode();
    }

    @Override
    public String toString() {
        return panelName;
    }
}

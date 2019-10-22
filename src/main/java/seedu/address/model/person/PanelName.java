package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a PanelName.
 */
public class PanelName {

    public static final String MESSAGE_CONSTRAINTS = "Panels names should be one of the following: wishlist, budget or "
            + "reminder";

    public final String panelName;

    public PanelName(String panelName) {
        requireNonNull(panelName);
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
        if (testLowerCase.equals("wishlist") || testLowerCase.equals("budget") || testLowerCase.equals("reminder")) {
            return true;
        } else {
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

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return panelName;
    }
}

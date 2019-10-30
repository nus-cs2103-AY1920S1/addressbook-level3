package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a PanelName.
 */
public class PanelName {

    public static final String MESSAGE_CONSTRAINTS = "Panel names should be one of the following: wishlist/w, "
            + "budget(s)/b, reminder(s)/r or autoexpense(s)/ae";

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

        ArrayList<String> acceptedInputs = new ArrayList<>(Arrays.asList("wishlist", "wish", "wishes", "w", "budget",
                "budgets", "b", "reminders", "reminder", "r", "autoexpenses", "autoexpense", "autoexp", "ae"));

        return acceptedInputs.contains(testLowerCase);
    }

    /**
     * Parses input {@code panelNameString} and returns {@code PanelName} with standardised panel name.
     */
    public static PanelName parse(String panelNameString) {
        String standardisedPanelName = "";

        switch (panelNameString.toLowerCase()) {
        case "wishlist":
            // Fallthrough
        case "wishes":
            // Fallthrough
        case "wish":
            // Fallthrough
        case "w":
            standardisedPanelName = "wishlist";
            break;
        case "budget":
            // Fallthrough
        case "budgets":
            // Fallthrough
        case "b":
            standardisedPanelName = "budget";
            break;
        case "reminders":
            // Fallthrough
        case "reminder":
            // Fallthrough
        case "r":
            standardisedPanelName = "reminder";
            break;
        case "autoexpense":
            // Fallthrough
        case "autoexpenses":
            // Fallthrough
        case "autoexp":
            // Fallthrough
        case "ae":
            standardisedPanelName = "autoexpense";
            break;
        default:
            // Do nothing. Input should have been checked if it is a valid panel name.
        }

        return new PanelName(standardisedPanelName);
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

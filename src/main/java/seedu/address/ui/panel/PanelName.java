package seedu.address.ui.panel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

/**
 * Represents a Panel Name which is used in the GUI.
 */
public class PanelName {
    public static final PanelName CURRENT = new PanelName("Current Page");

    // temporary
    public static final PanelName ALIASES_PANEL = new PanelName("Aliases");
    public static final PanelName EVENTS_PANEL = new PanelName("Events");
    public static final PanelName STATISTICS_PANEL = new PanelName("Statistics");

    public static final String MESSAGE_NAME_FORMAT = "\"%s\" is not a valid panel name. \n"
            + "Panel names can only have alphanumeric characters with whitespaces in between.";

    private static final String NAME_VALIDATION_REGEX = "\\p{Alnum}+(\\s+\\p{Alnum}+)*";

    private final String panelName;

    public PanelName(String panelName) {
        requireNonNull(panelName);
        String trimmed = panelName.trim();
        checkArgument(isValidPanelName(trimmed), String.format(MESSAGE_NAME_FORMAT, panelName));
        this.panelName = trimmed;
    }

    public static boolean isValidPanelName(String panelName) {
        return Pattern.compile(NAME_VALIDATION_REGEX).matcher(panelName).matches();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PanelName)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        PanelName otherPanelName = (PanelName) obj;
        return otherPanelName.panelName.equals(panelName);
    }

    @Override
    public int hashCode() {
        return panelName.hashCode();
    }

    @Override
    public String toString() {
        return "[Panel: " + panelName + "]";
    }
}

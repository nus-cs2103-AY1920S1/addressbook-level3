package seedu.address.ui.util;

/**
 * An enum containing the possible Themes.
 */
public enum Theme {

    LIGHT, DARK;

    public String getThemeAsString(Theme theme) {
        String output = "";
        if (theme == LIGHT) {
            output = "light";
        }
        if (theme == DARK) {
            output = "dark";
        }
        return output;
    }



}

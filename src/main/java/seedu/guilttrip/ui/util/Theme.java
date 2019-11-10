package seedu.guilttrip.ui.util;

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

    public String getThemeUrl(Theme theme) {
        if (theme == LIGHT) {
            return getClass().getResource("/view/LightTheme.css").toExternalForm();
        } else {
            return getClass().getResource("/view/DarkTheme.css").toExternalForm();
        }
    }

    public String getThemeUrl() {
        if (this == LIGHT) {
            return getClass().getResource("/view/LightTheme.css").toExternalForm();
        } else {
            return getClass().getResource("/view/DarkTheme.css").toExternalForm();
        }
    }

    public String getThemeExtensionUrl(Theme theme) {
        if (theme == LIGHT) {
            return getClass().getResource("/view/ExtensionsLight.css").toExternalForm();
        } else {
            return getClass().getResource("/view/ExtensionsDark.css").toExternalForm();

        }
    }

    public String getThemeExtensionUrl() {
        if (this == LIGHT) {
            return getClass().getResource("/view/ExtensionsLight.css").toExternalForm();
        } else {
            return getClass().getResource("/view/ExtensionsDark.css").toExternalForm();

        }
    }


}

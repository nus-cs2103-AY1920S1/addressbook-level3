package seedu.address.model.appsettings;

/**
 * Enums for either a light theme or a dark theme.
 */
public enum ThemeEnum {
    DARK("DarkTheme.css"), LIGHT("LightTheme.css");

    private String themeUrl;

    ThemeEnum(String s) {
        themeUrl = s;
    }

    /**
     * Gets the name of the .css file.
     *
     * @return Either "LightTheme.css" or "DarkTheme.css".
     */
    public String getThemeUrl() {
        return themeUrl;
    }
}

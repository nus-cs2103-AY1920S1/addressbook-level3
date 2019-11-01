package seedu.address.model.settings;

/**
 * Represents the current theme selected by the user for +Work.
 * Stores the required CSS Urls.
 */
public enum Theme {
    DARK(Theme.class.getClassLoader().getResource("view/DarkTheme.css").toExternalForm(),
            Theme.class.getClassLoader().getResource("view/Extensions.css").toExternalForm()),
    LIGHT(Theme.class.getClassLoader().getResource("view/LightTheme.css").toExternalForm(),
            Theme.class.getClassLoader().getResource("view/ExtensionsLight.css").toExternalForm());

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid theme code, please enter one of light or dark.";

    private String themeUrl;
    private String extensionUrl;

    /**
     * Create a theme enum with the associated CSS files.
     *
     * @param themeUrl an alternate name for the theme
     */
    Theme(String themeUrl, String extensionUrl) {
        this.themeUrl = themeUrl;
        this.extensionUrl = extensionUrl;
    }

    public String getExtensionUrl() {
        return extensionUrl;
    }

    public String getThemeUrl() {
        return themeUrl;
    }
}

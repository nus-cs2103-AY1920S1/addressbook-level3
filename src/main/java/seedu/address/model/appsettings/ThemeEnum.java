package seedu.address.model.appsettings;

/**
 * Enums for either a light theme or a dark theme.
 */
public enum ThemeEnum {
    DARK(ThemeEnum.class.getClassLoader().getResource("view/DarkTheme.css").toExternalForm(),
            ThemeEnum.class.getClassLoader().getResource("view/Extensions.css").toExternalForm()),
    LIGHT(ThemeEnum.class.getClassLoader().getResource("view/LightTheme.css").toExternalForm(),
            ThemeEnum.class.getClassLoader().getResource("view/ExtensionsLight.css").toExternalForm());

    private String themeUrl;
    private String extensionUrl;

    ThemeEnum(String s, String e) {
        themeUrl = s;
        extensionUrl = e;
    }

    /**
     * Gets the name of the .css file.
     *
     * @return Either "LightTheme.css" or "DarkTheme.css".
     */
    public String getThemeUrl() {
        return themeUrl;
    }

    public String getExtensionUrl() {
        return extensionUrl;
    }
}

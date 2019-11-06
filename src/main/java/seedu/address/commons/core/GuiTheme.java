package seedu.address.commons.core;

/**
 * GuiTheme is an enumeration for different GUI themes, such as light or dark.
 */
public enum GuiTheme {
    LIGHT("light", "view/LightTheme.css"),
    DARK("dark", "view/DarkTheme.css");

    public static final String MESSAGE_CONSTRAINTS = "This mode is not valid";

    private String modeName;
    private String cssString;

    GuiTheme(String modeName, String cssString) {
        this.modeName = modeName;
        this.cssString = cssString;
    }

    public String getCssString() {
        return cssString;
    }

    public String getModeName() {
        return modeName;
    }
}

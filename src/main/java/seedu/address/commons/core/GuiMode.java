package seedu.address.commons.core;

/**
 * GuiMode is an enumeration for different GUI themes, such as light or dark.
 */
public enum GuiMode {
    LIGHT("light", "view/LightMode.css"),
    DARK("dark", "view/DarkMode.css");

    public static final String MESSAGE_CONSTRAINTS = "This mode is not valid";

    private String modeName;
    private String cssString;

    GuiMode(String modeName, String cssString) {
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

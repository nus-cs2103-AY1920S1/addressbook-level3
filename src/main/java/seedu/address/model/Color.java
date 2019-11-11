package seedu.address.model;

/**
 * Color is an enumeration of color values for tags.
 */
public enum Color {
    RED("RED", "#FF0000"),
    ORANGE("ORANGE", "#FFA500"),
    YELLOW("YELLOW", "#FFFF00"),
    GREEN("GREEN", "#008000"),
    BLUE("BLUE", "#0000FF"),
    PURPLE("PURPLE", "#800080"),
    BROWN("BROWN", "#A52A2A"),
    MAGENTA("MAGENTA", "#FF00FF"),
    TAN("TAN", "#D2B48C"),
    CYAN("CYAN", "#00FFFF"),
    OLIVE("OLIVE", "#808000"),
    MAROON("MAROON", "#800000"),
    NAVY("NAVY", "#000080"),
    AQUAMARINE("AQUAMARINE", "#7FFFD4"),
    TURQUOISE("TURQUOISE", "#40E0D0"),
    SILVER("SILVER", "#C0C0C0"),
    LIME("LIME", "#00FF00"),
    TEAL("TEAL", "#008080"),
    INDIGO("INDIGO", "#4B0082"),
    VIOLET("VIOLET", "#EE82EE"),
    PINK("PINK", "#FFC0CB"),
    BLACK("BLACK", "#000000"),
    WHITE("WHITE", "#FFFFFF"),
    GREY("GREY", "#808080");

    public static final String MESSAGE_CONSTRAINTS = "This color is not valid";

    private String colorName;
    private String hexValue;

    Color(String colorName, String hexValue) {
        this.colorName = colorName;
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }

    public String getColorName() {
        return colorName;
    }
}

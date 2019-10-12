package seedu.address.model;

/**
 * Color is an enumeration of color values for tags.
 */
public enum Color {
    // TODO: set colors. These are placeholder values
    RED;

    // place holder

    /**
     * Returns a color given a string with the name of the color.
     */
    public Color toColor(String value) {
        if (value.equals("red")) {
            return RED;
        } else {
            return RED;
        }
    }
}

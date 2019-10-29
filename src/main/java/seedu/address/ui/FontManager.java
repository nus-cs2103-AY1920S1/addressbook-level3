package seedu.address.ui;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Font Manager to manage fonts.
 */
public class FontManager {

    public static final List<FontName> FONTS = generateFontNames();
    public static final String MESSAGE_CONSTRAINTS = "Font name should be one of the following: " + FONTS.toString();

    private FontName currentFontName;

    /**
     * Empty constructor for FontManager class.
     */
    public FontManager() {}

    /*public FontManager(FontName currentFontName) {
        requireAllNonNull(fonts, currentFontName);
        checkArgument(isValidFontName(currentFontName), MESSAGE_CONSTRAINTS);
        this.currentFontName = currentFontName;
    }*/

    /**
     * Generates a list of {@code FontName} from their string equivalents.
     * @return
     */
    private static List<FontName> generateFontNames() {
        List<String> fontNameStrings = new ArrayList<>(Arrays.asList("arial", "calibri", "cambria", "candara",
                "garamond", "georgia", "rockwell", "segoe UI", "serif", "verdana"));
        List<FontName> fontNames = new ArrayList<>(0);
        for (String fontNameString : fontNameStrings) {
            fontNames.add(new FontName(fontNameString));
        }
        return fontNames;
    }

    public List<FontName> getFonts() {
        return FONTS;
    }

    public FontName getCurrentFontName() {
        return this.currentFontName;
    }

    public void setCurrentFontName(FontName newFontName) {
        requireAllNonNull(newFontName);
        checkArgument(isValidFontName(newFontName), MESSAGE_CONSTRAINTS);
        this.currentFontName = newFontName;
    }

    /**
     * Returns true if specified font name exists in the list of valid font names.
     */
    private static boolean isValidFontName(FontName test) {
        return FONTS.contains(test);
    }

    /**
     * Returns true if specified font name exists in the list of valid font names.
     */
    public static boolean isValidFontName(String test) {
        return FONTS.contains(new FontName(test));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FontManager
                && currentFontName.equals(((FontManager) other).getCurrentFontName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(FONTS, currentFontName);
    }

}

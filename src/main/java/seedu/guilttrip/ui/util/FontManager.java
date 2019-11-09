package seedu.guilttrip.ui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Font Manager to manage fonts.
 */
public class FontManager {

    public static final ArrayList<FontName> FONTS = FontName.getAllFontNames();
    public static final String MESSAGE_CONSTRAINTS = "Font name should be one of the following: " + FONTS.toString();

    private FontName currentFontName;

    /**
     * Empty constructor for FontManager class.
     */
    public FontManager() {}

    public List<FontName> getFontNames() {
        return FONTS;
    }

    public List<String> getFontsAsStrings() {
        return FontName.getAllFontNameStrings();
    }

    public FontName getCurrentFontName() {
        return this.currentFontName;
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

package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a FontName.
 */
public class FontName {

    public final String fontName;

    public FontName(String fontName) {
        requireAllNonNull(fontName);
        this.fontName = fontName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FontName // instanceof handles nulls
                && fontName.equals(((FontName) other).fontName)); // state check
    }

    @Override
    public int hashCode() {
        return fontName.hashCode();
    }

    @Override
    public String toString() {
        return fontName;
    }
}

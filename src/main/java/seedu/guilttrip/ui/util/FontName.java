package seedu.guilttrip.ui.util;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Represents a FontName.
 */
public enum FontName {

    ARIAL, CALIBRI, CAMBRIA, CANDARA, GARAMOND, GEORGIA, ROCKWELL, SEGOE_UI, SERIF, VERDANA;

    /**
     * Parses the specified {@code fontNameString} and returns a {@code FontName}.
     * @throws ParseException if input string is not a recognised FontName
     */
    public static FontName parse(String fontNameString) throws ParseException {
        switch (fontNameString.trim()) {
        case "arial":
            return ARIAL;
        case "calibri":
            return CALIBRI;
        case "cambria":
            return CAMBRIA;
        case "candara":
            return CANDARA;
        case "garamond":
            return GARAMOND;
        case "georgia":
            return GEORGIA;
        case "rockwell":
            return ROCKWELL;
        case "segoe UI":
            return SEGOE_UI;
        case "serif":
            return SERIF;
        case "verdana":
            return VERDANA;
        default:
            throw new ParseException("Invalid font name");
        }
    }

    public static ArrayList<FontName> getAllFontNames() {
        return new ArrayList<>(Arrays.asList(ARIAL, CALIBRI, CANDARA, GARAMOND, GEORGIA, ROCKWELL, SEGOE_UI, SERIF,
                VERDANA));
    }

    public static ArrayList<String> getAllFontNameStrings() {
        ArrayList<String> fontNameStrings = new ArrayList<>();
        ArrayList<FontName> fontNames = getAllFontNames();
        for (FontName fontName : fontNames) {
            fontNameStrings.add(FontName.toLowerCaseString(fontName));
        }
        return fontNameStrings;
    }

    /**
     * Returns given {@code fontName} as a String in lower case, except for {@code SEGOE_UI} which is case sensitive.
     */
    public static String toLowerCaseString(FontName fontName) {
        if (fontName == SEGOE_UI) {
            return "segoe UI";
        } else {
            String fontNameString = fontName + "";
            return fontNameString.toLowerCase();
        }
    }


}

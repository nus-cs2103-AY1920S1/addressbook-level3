package seedu.weme.logic.parser.util;

import seedu.weme.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final String PREFIX_DESCRIPTION_STRING = "d/";
    public static final String PREFIX_FILEPATH_STRING = "p/";
    public static final String PREFIX_TAG_STRING = "t/";
    public static final String PREFIX_NAME_STRING = "n/";
    public static final String PREFIX_TEXT_STRING = "t/";
    public static final String PREFIX_X_COORDINATE_STRING = "x/";
    public static final String PREFIX_Y_COORDINATE_STRING = "y/";
    public static final String PREFIX_COLOR_STRING = "c/";
    public static final String PREFIX_STYLE_STRING = "S/";
    public static final String PREFIX_SIZE_STRING = "s/";

    public static final Prefix PREFIX_DESCRIPTION = new Prefix(PREFIX_DESCRIPTION_STRING);
    public static final Prefix PREFIX_FILEPATH = new Prefix(PREFIX_FILEPATH_STRING);
    public static final Prefix PREFIX_TAG = new Prefix(PREFIX_TAG_STRING);
    public static final Prefix PREFIX_NAME = new Prefix(PREFIX_NAME_STRING);
    public static final Prefix PREFIX_TEXT = new Prefix(PREFIX_TEXT_STRING);
    public static final Prefix PREFIX_X_COORDINATE = new Prefix(PREFIX_X_COORDINATE_STRING);
    public static final Prefix PREFIX_Y_COORDINATE = new Prefix(PREFIX_Y_COORDINATE_STRING);
    public static final Prefix PREFIX_COLOR = new Prefix(PREFIX_COLOR_STRING);
    public static final Prefix PREFIX_STYLE = new Prefix(PREFIX_STYLE_STRING);
    public static final Prefix PREFIX_SIZE = new Prefix(PREFIX_SIZE_STRING);

}

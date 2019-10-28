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

    public static final Prefix PREFIX_DESCRIPTION = new Prefix(PREFIX_DESCRIPTION_STRING);
    public static final Prefix PREFIX_FILEPATH = new Prefix(PREFIX_FILEPATH_STRING);
    public static final Prefix PREFIX_TAG = new Prefix(PREFIX_TAG_STRING);
    public static final Prefix PREFIX_NAME = new Prefix(PREFIX_NAME_STRING);

}

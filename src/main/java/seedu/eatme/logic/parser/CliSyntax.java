package seedu.eatme.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("\\n");
    public static final Prefix PREFIX_ADDRESS = new Prefix("\\a");
    public static final Prefix PREFIX_CATEGORY = new Prefix("\\c");
    public static final Prefix PREFIX_TAG = new Prefix("\\t");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("\\d");
    public static final Prefix PREFIX_COST = new Prefix("\\p");
    public static final Prefix PREFIX_RATING = new Prefix("\\r");
    public static final Prefix PREFIX_DATE = new Prefix("\\w");
    public static final Prefix PREFIX_USER = new Prefix("\\u");
}

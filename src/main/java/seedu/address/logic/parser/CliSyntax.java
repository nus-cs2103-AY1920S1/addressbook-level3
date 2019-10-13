package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_DATE_START = new Prefix("ds/");
    public static final Prefix PREFIX_DATE_END = new Prefix("de/");
    public static final Prefix PREFIX_DATE_TIME_START = new Prefix("dts/");
    public static final Prefix PREFIX_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_INVENTORY = new Prefix("inv/");
    public static final Prefix PREFIX_BOOKING = new Prefix("bo/");
    public static final Prefix PREFIX_DATA_FILE_PATH = new Prefix("fp/");
}

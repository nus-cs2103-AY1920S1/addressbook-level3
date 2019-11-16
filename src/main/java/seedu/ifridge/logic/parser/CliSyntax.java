package seedu.ifridge.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("a/");
    public static final Prefix PREFIX_REMINDER = new Prefix("r/");
    public static final Prefix PREFIX_SORT = new Prefix("by/");
    public static final Prefix PREFIX_LIST = new Prefix("d/");
    public static final Prefix PREFIX_MONTH = new Prefix("m/");
    public static final Prefix PREFIX_START_MONTH = new Prefix("sm/");
    public static final Prefix PREFIX_END_MONTH = new Prefix("em/");
    public static final Prefix PREFIX_ITEM_INDEX = new Prefix("i/");

}

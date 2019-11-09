package seedu.ichifund.logic.parser;

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
    public static final Prefix PREFIX_AMOUNT = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("de/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_MONTH = new Prefix("m/");
    public static final Prefix PREFIX_DAY = new Prefix("d/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_TRANSACTION_TYPE = new Prefix("ty/");

    public static final Prefix PREFIX_END_DAY = new Prefix("ed/");
    public static final Prefix PREFIX_END_MONTH = new Prefix("em/");
    public static final Prefix PREFIX_END_YEAR = new Prefix("ey/");
    public static final Prefix PREFIX_MONTH_END_OFFSET = new Prefix("eo/");
    public static final Prefix PREFIX_MONTH_START_OFFSET = new Prefix("so/");
    public static final Prefix PREFIX_START_DAY = new Prefix("sd/");
    public static final Prefix PREFIX_START_MONTH = new Prefix("sm/");
    public static final Prefix PREFIX_START_YEAR = new Prefix("sy/");

}

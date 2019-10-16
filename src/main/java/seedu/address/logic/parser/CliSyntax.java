package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("w/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_CALLER_NUMBER = new Prefix("c/");

    public static final Prefix PREFIX_CALLER = new Prefix("c/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
}

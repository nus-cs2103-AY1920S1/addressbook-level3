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
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("s/");
    public static final Prefix PREFIX_TIME = new Prefix("c/");
    public static final Prefix PREFIX_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_EXPENSE = new Prefix("ex/");
    public static final Prefix PREFIX_RECIPIENT = new Prefix("r/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pa/");
    public static final Prefix PREFIX_ACCOUNT = new Prefix("ac/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("su/");
    public static final Prefix PREFIX_MESSAGE = new Prefix("me/");
    public static final Prefix PREFIX_DONE = new Prefix("d/");
}

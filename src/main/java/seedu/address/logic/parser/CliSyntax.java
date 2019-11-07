package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_DESC = new Prefix("a/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("$/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_SHARE = new Prefix("s/");
    public static final Prefix PREFIX_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_MONTH = new Prefix("m/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
}

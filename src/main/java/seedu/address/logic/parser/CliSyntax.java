package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PREDICATE = new Prefix("pred/");
    public static final Prefix PREFIX_ACTION = new Prefix("act/");

    public static final Prefix PREFIX_AMOUNT = new Prefix("x/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_DATE = new Prefix("w/");
    public static final Prefix PREFIX_SORT = new Prefix("s/");

    // Loans
    public static final Prefix PREFIX_PERSON = new Prefix("p/");
}

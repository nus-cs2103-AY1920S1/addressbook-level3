package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_SERIAL_NUMBER = new Prefix("sn/");
    public static final Prefix PREFIX_AUTHOR = new Prefix("a/");
    public static final Prefix PREFIX_GENRE = new Prefix("g/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_BORROWER_ID = new Prefix("id/");
    public static final Prefix PREFIX_LOAN_PERIOD = new Prefix("lp/");
    public static final Prefix PREFIX_RENEW_PERIOD = new Prefix("rp/");
    public static final Prefix PREFIX_FINE_INCREMENT = new Prefix("fi/");
    public static final Prefix PREFIX_MAX_RENEWS = new Prefix("mr/");
    public static final Prefix PREFIX_FLAG = new Prefix("-");
    public static final Prefix PREFIX_DOLLAR = new Prefix("$");
}

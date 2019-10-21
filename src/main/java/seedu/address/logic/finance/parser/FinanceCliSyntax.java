package seedu.address.logic.finance.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class FinanceCliSyntax {

    /* Prefix definitions common to all log entries*/
    public static final Prefix PREFIX_AMOUNT = new Prefix("<amt>");
    public static final Prefix PREFIX_DAY = new Prefix("<day>");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("<item>");
    public static final Prefix PREFIX_CATEGORY = new Prefix("<cat>");
    public static final Prefix PREFIX_TRANSACTION_METHOD = new Prefix("<met>");

    /* Other prefix definitions */
    public static final Prefix PREFIX_PLACE = new Prefix("<place>");
    public static final Prefix PREFIX_FROM = new Prefix("<from>");
    public static final Prefix PREFIX_TO = new Prefix("<to>");

}

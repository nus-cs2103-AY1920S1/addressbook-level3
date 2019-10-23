package seedu.address.logic.finance.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class FinanceCliSyntax {

    /* Basic prefix definitions */
    public static final Prefix PREFIX_AMOUNT = new Prefix("<amt>");
    public static final Prefix PREFIX_DAY = new Prefix("<day>");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("<item>");

    /* Other prefix definitions */
    public static final Prefix PREFIX_CATEGORY = new Prefix("<cat>");
    public static final Prefix PREFIX_PLACE = new Prefix("<place>");
    public static final Prefix PREFIX_TRANSACTION_METHOD = new Prefix("<met>");

}

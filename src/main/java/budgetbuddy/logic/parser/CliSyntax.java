package budgetbuddy.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ACCOUNT = new Prefix("a/");
    public static final Prefix PREFIX_ACTION = new Prefix("act/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("x/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_FROM = new Prefix("f/");
    public static final Prefix PREFIX_UNTIL = new Prefix("u/");
    public static final Prefix PREFIX_DATE = new Prefix("w/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PREDICATE = new Prefix("pred/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // Sorting
    public static final Prefix PREFIX_SORT = new Prefix("s/");
    public static final String SORT_ARG_DATE = "w";
    public static final String SORT_ARG_PERSON = "p";
    public static final String SORT_ARG_AMOUNT = "x";

    // Loans
    public static final Prefix PREFIX_PERSON = new Prefix("p/");
    public static final Prefix PREFIX_USER = new Prefix("me/");
    public static final String KEYWORD_LOAN_OUT = "out";
    public static final String KEYWORD_LOAN_IN = "in";
    public static final String KEYWORD_LOAN_PAID = "paid";
    public static final String KEYWORD_LOAN_UNPAID = "unpaid";

    // Transactions
    public static final Prefix PREFIX_RECURRENCE = new Prefix("r/");

    // Scripts
    public static final Prefix PREFIX_SCRIPT_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_SCRIPT_PATH = new Prefix("p/");
    public static final Prefix PREFIX_SCRIPT_SOURCE = new Prefix("s/");

    // Date examples
    public static final String DATE_EXAMPLE = "4/20/2420";
}

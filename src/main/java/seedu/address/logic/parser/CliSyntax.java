package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE = new Prefix("on/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("no/");
    public static final Prefix PREFIX_PAY = new Prefix("p/");
    public static final Prefix PREFIX_SalaryPaid = new Prefix("s/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_JOIN_DATE = new Prefix("on/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EVENT_VENUE = new Prefix("at/");
    public static final Prefix PREFIX_EVENT_MANPOWER_NEEDED = new Prefix("m/");
    public static final Prefix PREFIX_EVENT_START_DATE = new Prefix("on/");
    public static final Prefix PREFIX_EVENT_END_DATE = new Prefix("till/");
    public static final Prefix PREFIX_EVENT_TIME = new Prefix("time/");
    public static final Prefix PREFIX_EMPLOYEE_NUMBER = new Prefix("n/"); // for allocatem command
    public static final Prefix PREFIX_EMPLOYEE_ID = new Prefix("id/"); // for allocatem command
    public static final Prefix PREFIX_MANPOWER_TO_ADD = new Prefix("n/"); // for allocate command
    public static final Prefix PREFIX_YEAR_MONTH = new Prefix("for/");
}

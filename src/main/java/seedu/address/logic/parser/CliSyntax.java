package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // Person prefix
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("dob/");
    public static final Prefix PREFIX_POLICY = new Prefix("pol/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // Policy prefix
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_COVERAGE = new Prefix("c/");
    public static final Prefix PREFIX_DAYS = new Prefix("days/");
    public static final Prefix PREFIX_MONTHS = new Prefix("months/");
    public static final Prefix PREFIX_YEARS = new Prefix("years/");
    public static final Prefix PREFIX_PRICE = new Prefix ("p/");
    public static final Prefix PREFIX_START_AGE = new Prefix ("sa/");
    public static final Prefix PREFIX_END_AGE = new Prefix("ea/");
    public static final Prefix PREFIX_CRITERIA = new Prefix("cr/");

    // Prefixes for assign/unassign policy commands
    public static final Prefix PREFIX_POLICY_INDEX = new Prefix("polindex/");
    public static final Prefix PREFIX_PERSON_INDEX = new Prefix("perindex/");

    public static final Prefix PREFIX_MERGE = new Prefix("m/");
}

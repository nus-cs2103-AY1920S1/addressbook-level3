package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/", "description");
    public static final Prefix PREFIX_PRICE = new Prefix("p/", "amount");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/", "category");
    public static final Prefix PREFIX_PERIOD = new Prefix("pr/", "period");
    public static final Prefix PREFIX_TIMESTAMP = new Prefix("date/", "date");
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/", "start date");
    public static final Prefix PREFIX_END_DATE = new Prefix("ed/", "end date");
    public static final Prefix PREFIX_FIRST_START_DATE = new Prefix("sd1/", "start date1");
    public static final Prefix PREFIX_SECOND_START_DATE = new Prefix("sd2/", "start date2");
    public static final Prefix PREFIX_ALIAS_ALIAS_NAME = new Prefix("a/", "alias name");
    public static final Prefix PREFIX_ALIAS_ALIAS_INPUT = new Prefix("i/", "alias input");
}



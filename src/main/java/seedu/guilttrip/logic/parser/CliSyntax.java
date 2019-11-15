package seedu.guilttrip.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TYPE = new Prefix("typ/", "TYPE");
    public static final Prefix PREFIX_DESC = new Prefix("n/", "DESCRIPTION");
    public static final Prefix PREFIX_CATEGORY = new Prefix("cat/", "CATEGORY");
    public static final Prefix PREFIX_AMOUNT = new Prefix("amt/", "AMOUNT");
    public static final Prefix PREFIX_OLD_NAME = new Prefix("o/", "OLD_NAME");
    public static final Prefix PREFIX_DATE = new Prefix("d/", "DATE");
    public static final Prefix PREFIX_SEQUENCE = new Prefix("s/", "SEQUENCE");
    public static final Prefix PREFIX_TAG = new Prefix("tg/", "TAG");
    public static final Prefix PREFIX_PERIOD = new Prefix("p/", "PERIOD");
    public static final Prefix PREFIX_INDEX = new Prefix("i/", "INDEX");
    public static final Prefix PREFIX_PARAM = new Prefix("pa/", "PARAM");
    public static final Prefix PREFIX_FREQ = new Prefix("f/", "FREQUENCY");
    public static final Prefix PREFIX_COORDINATES = new Prefix("c/", "COORDINATES");
    public static final Prefix PREFIX_BOOL = new Prefix("b/", "COORDINATES");
    public static final Prefix PREFIX_UPPER_BOUND = new Prefix("u.b/", "UPPERBOUND");
    public static final Prefix PREFIX_LOWER_BOUND = new Prefix("l.b/", "LOWERBOUND");
    public static final Prefix PREFIX_START_DATE = new Prefix("start/", "START_DATE");
    public static final Prefix PREFIX_END_DATE = new Prefix("end/", "END_DATE");
}

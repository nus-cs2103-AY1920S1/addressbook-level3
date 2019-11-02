package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("w/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_CALLER_NUMBER = new Prefix("c/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");

    /* For vehicle use */
    public static final Prefix PREFIX_VEHICLE = new Prefix("v/");
    public static final Prefix PREFIX_DISTRICT = new Prefix("dist/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_VTYPE = new Prefix("vt/");
    public static final Prefix PREFIX_VNUM = new Prefix("vn/");
    public static final Prefix PREFIX_AVAIL = new Prefix("a/");

    /* For all auto-fill prompts */
    public static final Prefix PREFIX_AUTO = new Prefix("auto/");

    /* For search flags */
    public static final Prefix SEARCH_PREFIX_ID = new Prefix("id/");
    public static final Prefix SEARCH_PREFIX_DESCRIPTION = new Prefix("desc/");
    public static final Prefix SEARCH_PREFIX_OPERATOR = new Prefix("op/");
    public static final Prefix SEARCH_PREFIX_DISTRICT = new Prefix("ds/");
    public static final Prefix SEARCH_PREFIX_VTYPE = new Prefix("vtype/");
    public static final Prefix SEARCH_PREFIX_VNUM = new Prefix("vnum/");

    public static final Prefix SEARCH_PREFIX_SELF = new Prefix("self");
}

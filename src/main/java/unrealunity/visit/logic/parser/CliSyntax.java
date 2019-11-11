package unrealunity.visit.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_ALIAS_NAME = new Prefix("l/");
    public static final Prefix PREFIX_ALIAS_VALUE = new Prefix("v/");

    public static final Prefix PREFIX_VISIT = new Prefix("v/");
    public static final Prefix PREFIX_DELETE_VISIT = new Prefix("d/");
    public static final Prefix PREFIX_EDIT_VISIT = new Prefix("i/");
    public static final Prefix PREFIX_DAYS = new Prefix("d/");


}

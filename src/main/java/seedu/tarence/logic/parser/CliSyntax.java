package seedu.tarence.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_MATNO = new Prefix("mat/");
    public static final Prefix PREFIX_NUSID = new Prefix("nusid/");
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_TUTORIAL_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TUTORIAL_DAY = new Prefix("d/");
    public static final Prefix PREFIX_TUTORIAL_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_TUTORIAL_START_TIME = new Prefix("st/");
    public static final Prefix PREFIX_TUTORIAL_WEEKS = new Prefix("w/");
    public static final Prefix PREFIX_TUTORIAL_DURATION_IN_MINUTES = new Prefix("dur/");
}

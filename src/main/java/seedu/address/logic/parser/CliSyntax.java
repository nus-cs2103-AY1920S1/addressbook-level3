package seedu.address.logic.parser;

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
    public static final Prefix PREFIX_ORGANISATION = new Prefix("o/");
    public static final Prefix PREFIX_TIE_BREAK = new Prefix("tb/");
    public static final Prefix PREFIX_SUBJECT_NAME = new Prefix("s/");
    public static final Prefix PREFIX_PROJECT_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_FILE_PATH = new Prefix("fp/");
    public static final String PREFIX_ENTITY_MENTOR = "M";
    public static final String PREFIX_ENTITY_PARTICIPANT = "P";
    public static final String PREFIX_ENTITY_TEAM = "T";
    public static final String ENTITY_MENTOR = "mentor";
    public static final String ENTITY_PARTICIPANT = "participant";
    public static final String ENTITY_TEAM = "team";
    public static final String SCORE_UPDATE = "set";
    public static final String SCORE_ADD = "add";
    public static final String SCORE_SUBTRACT = "sub";
    public static final String SCORE_RESET = "reset";
}

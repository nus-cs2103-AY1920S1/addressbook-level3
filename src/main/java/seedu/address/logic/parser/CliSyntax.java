package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_IMAGE = new Prefix("i/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_INDEXES = new Prefix("#/");

    /* Flag definitions */
    public static final Flag FLAG_PERSON = new Flag("-p");
    public static final Flag FLAG_EVENT = new Flag("-e");
    public static final Flag FLAG_TRAINING = new Flag("-t");
    public static final Flag FLAG_ABSENT = new Flag("-a");
    public static final Flag FLAG_RECORD = new Flag("-r");

}

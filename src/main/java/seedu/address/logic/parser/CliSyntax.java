package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ID = new Prefix("-id ");
    public static final Prefix PREFIX_NAME = new Prefix("-name ");
    public static final Prefix PREFIX_PHONE = new Prefix("-phone ");
    public static final Prefix PREFIX_EMAIL = new Prefix("-email ");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-address ");
    public static final Prefix PREFIX_ENTRY = new Prefix("-entry ");
    public static final Prefix PREFIX_TAG = new Prefix("-tag ");
    public static final Prefix PREFIX_START = new Prefix("-start ");
    public static final Prefix PREFIX_END = new Prefix("-end ");
    public static final Prefix PREFIX_REOCCURRING = new Prefix("-reoccur ");
    public static final Prefix PREFIX_REOCCURRING_TIMES = new Prefix("-num ");
}

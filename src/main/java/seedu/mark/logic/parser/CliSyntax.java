package seedu.mark.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_FOLDER = new Prefix("f/");
    public static final Prefix PREFIX_HIGHLIGHT = new Prefix("h/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NOT_FOLDER = new Prefix("nf/");
    public static final Prefix PREFIX_NOT_NAME = new Prefix("nn/");
    public static final Prefix PREFIX_NOT_URL = new Prefix("nu/");
    public static final Prefix PREFIX_NOTE = new Prefix("n/");
    public static final Prefix PREFIX_PARAGRAPH = new Prefix("p/");
    public static final Prefix PREFIX_PARENT_FOLDER = new Prefix("p/");
    public static final Prefix PREFIX_NEW_FOLDER = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_TO_NEW_PARAGRAPH = new Prefix("to/");
    public static final Prefix PREFIX_URL = new Prefix("u/");
}

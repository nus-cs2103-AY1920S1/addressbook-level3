package seedu.module.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ACTION = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_LINK = new Prefix("l/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NEW_NAME = new Prefix("nn/");
    public static final Prefix PREFIX_NEW_LINK = new Prefix("nl/");
    public static final Prefix PREFIX_TASK_LIST_NUMBER = new Prefix("task/");
    public static final Prefix PREFIX_TAG = new Prefix("p/");
}

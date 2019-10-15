package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_CONTENT = new Prefix("c/");
    public static final Prefix PREFIX_NUMBER = new Prefix("n/");
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("d/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
    public static final Prefix PREFIX_DATE = new Prefix("dt/");
    public static final Prefix PREFIX_TIME = new Prefix("tm/");
}

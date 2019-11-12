package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_CONTENT = new Prefix("c/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/");
    public static final Prefix PREFIX_NOTE_FRAGMENT_CONTENT = new Prefix("C/");
    public static final Prefix PREFIX_NOTE_FRAGMENT_TAG = new Prefix("TAG/");
    public static final Prefix PREFIX_NOTE_FRAGMENT_START = new Prefix("/*");
    public static final Prefix PREFIX_NOTE_FRAGMENT_END = new Prefix("*/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");
    public static final Prefix PREFIX_QUESTION = new Prefix("q/");
    public static final Prefix PREFIX_ANSWER = new Prefix("a/");

    public static final Prefix PREFIX_ALL = new Prefix("all");
}

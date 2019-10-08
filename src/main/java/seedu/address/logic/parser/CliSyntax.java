package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    // ADDRESS BOOK PREFIX
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    // END OF ADDRESS BOOK PREFIX

    public static final Prefix PREFIX_QUESTION = new Prefix("question/");
    public static final Prefix PREFIX_ANSWER = new Prefix("answer/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");

    public static final Prefix PREFIX_LIST = new Prefix("list");
    public static final Prefix PREFIX_DELETE = new Prefix("delete");

}

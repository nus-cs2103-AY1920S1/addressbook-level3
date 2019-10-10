package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_QUESTION_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_QUESTION = new Prefix("q/");
    public static final Prefix PREFIX_CORRECT = new Prefix("y/");
    public static final Prefix PREFIX_WRONG = new Prefix("x/");
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("diff/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("cat/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}

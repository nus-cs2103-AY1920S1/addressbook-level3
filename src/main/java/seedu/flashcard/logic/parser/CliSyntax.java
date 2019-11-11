package seedu.flashcard.logic.parser;

/**
 * Contains Command Line Interface syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_QUESTION = new Prefix("q/");
    public static final Prefix PREFIX_DEFINITION = new Prefix("d/");
    public static final Prefix PREFIX_CHOICE = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ANSWER = new Prefix("a/");
    public static final Prefix PREFIX_DURATION = new Prefix("s/");
}

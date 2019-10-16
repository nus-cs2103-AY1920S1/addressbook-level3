package tagline.logic.parser.note;

import tagline.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class NoteCliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("--T ");
    public static final Prefix PREFIX_CONTENT = new Prefix("--c ");
    public static final Prefix PREFIX_TAG = new Prefix("--t ");

}

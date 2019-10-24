package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RemoveDislikeCommand;

//@@author fatclarence
/**
 * Contains information on removedislike command.
 */
public class RemoveDislikeInfo {

    public static final String COMMAND_WORD = RemoveDislikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "removedislike command allows you to \n"
            + "remove your previously added dislikes, \n"
            + "a list of foods that you want to see.\n\n";

    public static final String USAGE = "removedislike c/Chinese t/Cheap t/Healthy\n";

    public static final String OUTPUT = "You will see a success message and your \n"
            + "disliked categories, tags and locations will be removed.";
}

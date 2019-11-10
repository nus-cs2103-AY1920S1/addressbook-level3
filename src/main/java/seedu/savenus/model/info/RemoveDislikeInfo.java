package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RemoveDislikeCommand;

//@@author robytanama
/**
 * Contains information on removedislike command.
 */
public class RemoveDislikeInfo {

    public static final String COMMAND_WORD = RemoveDislikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "RemoveDislike command allows you to remove your previously stated "
            + "dislikes.\n\n"
            + "The removed dislikes will depend on the following factors:\n"
            + "Dislikes you stated previously\n\n";

    public static final String USAGE = "removedislike c/Chinese t/Cheap t/Healthy\n";

    public static final String OUTPUT = "Category Chinese, Tags Cheap and Healthy will be removed from your list "
            + "of dislikes.";
}

package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RemoveLikeCommand;

//@@author robytanama
/**
 * Contains information on removedislike command.
 */
public class RemoveLikeInfo {

    public static final String COMMAND_WORD = RemoveLikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "RemoveLike command allows you to remove your previously stated "
            + "likes.\n\n"
            + "The removed likes will depend on the following factors:\n"
            + "Likes you stated previously\n\n";


    public static final String USAGE = "removedlikes c/Chinese t/Cheap t/Healthy\n";

    public static final String OUTPUT = "Category Chinese, Tags Cheap and Healthy will be removed from your list "
            + "of likes.";
}

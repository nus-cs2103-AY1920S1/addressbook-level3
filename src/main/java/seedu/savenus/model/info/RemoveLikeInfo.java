package seedu.savenus.model.info;

import seedu.savenus.logic.commands.RemoveLikeCommand;

//@@author fatclarence
/**
 * Contains information on removedislike command.
 */
public class RemoveLikeInfo {

    public static final String COMMAND_WORD = RemoveLikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "removelike command allows you to \n"
            + "remove your previously added likes, \n"
            + "The liked categories, tags and locations must exist before they can be removed.\n\n";

    public static final String USAGE = "removelikes c/Chinese t/Cheap t/Healthy\n";

    public static final String OUTPUT = "You will see a success message and your \n"
            + "liked categories, tags and locations will be removed.";
}

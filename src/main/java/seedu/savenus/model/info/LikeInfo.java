package seedu.savenus.model.info;

import seedu.savenus.logic.commands.LikeCommand;

/**
 * Contains information on like command.
 */
public class LikeInfo {

    public static final String COMMAND_WORD = LikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "The like command allows you to \n"
            + "like a particular tag, category or location.\n"
            + "The liked tags, categories and locations must not exist \n"
            + "in the user's dislikes.\n\n";

    public static final String USAGE = "like c/Chinese t/Cheap t/Healthy l/University Town\n";

    public static final String OUTPUT = "You will see a success message and your \n"
            + "liked categories, tags and locations will be added.";
}

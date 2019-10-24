package seedu.savenus.model.info;

import seedu.savenus.logic.commands.DislikeCommand;

/**
 * Contains information on like command.
 */
public class DislikeInfo {

    public static final String COMMAND_WORD = DislikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "like command allows you to \n"
            + "dislike a particular tag, category or location.\n"
            + "The liked tags, categories and locations must not exist\n"
            + "in the user's likes.\n\n";


    public static final String USAGE = "dislike c/Chinese t/Cheap t/Healthy l/University Town\n";

    public static final String OUTPUT = "You will see a success message and your \n"
            + "disliked categories, tags and locations will be added.";
}

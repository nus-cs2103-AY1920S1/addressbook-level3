package seedu.savenus.model.info;

import seedu.savenus.logic.commands.DislikeCommand;

//@@author robytanama
/**
 * Contains information on like command.
 */
public class DislikeInfo {

    public static final String COMMAND_WORD = DislikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "Dislike command allows your to state your preferences.\n"
            + "Do note that only one field is compulsory but multiple fields can be added\n\n"
            + "The dislike feature will depend on the following factors:\n"
            + "Category\n"
            + "Tags\n"
            + "Location\n\n";

    public static final String USAGE = "dislike c/Chinese t/Cheap t/Healthy l/University Town\n";

    public static final String OUTPUT = "The app will store the information that you have disliked Food with:\n"
            + "Category: Chinese, Tags: Cheap and Healthy, Location: University Town";
}

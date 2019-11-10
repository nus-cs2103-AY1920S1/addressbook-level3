package seedu.savenus.model.info;

import seedu.savenus.logic.commands.LikeCommand;

//@@author robytanama
/**
 * Contains information on like command.
 */
public class LikeInfo {

    public static final String COMMAND_WORD = LikeCommand.COMMAND_WORD;

    public static final String INFORMATION = "Like command allows your to state your preferences.\n"
            + "Do note that only one field is compulsory but multiple fields can be added\n\n"
            + "The like feature will depend on the following factors:\n"
            + "Category\n"
            + "Tags\n"
            + "Location\n\n";

    public static final String USAGE = "like c/Chinese t/Cheap t/Healthy l/University Town\n";

    public static final String OUTPUT = "The app will store the information that you have liked Food with:\n"
            + "Category: Chinese, Tags: Cheap and Healthy, Location: University Town";
}

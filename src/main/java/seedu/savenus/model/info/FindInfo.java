package seedu.savenus.model.info;

import seedu.savenus.logic.commands.FindCommand;

//@@author robytanama
/**
 * Contains information on Find command.
 */
public class FindInfo {

    public static final String COMMAND_WORD = FindCommand.COMMAND_WORD;

    public static final String INFORMATION = "Find command allows you to get a list of Food items "
            + "containing the specified keyword.\n\n"
            + "The list will depend on the following factor:\n"
            + "Keyword\n\n";

    public static final String USAGE = "find mala";

    public static final String OUTPUT = "New list of Food item containing the word mala will be displayed.";
}

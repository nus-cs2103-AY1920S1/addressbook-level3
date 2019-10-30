package seedu.savenus.model.info;

import seedu.savenus.logic.commands.AutoSortCommand;

/**
 * Contains information on AutoSort command.
 */
public class AutoSortInfo {
    public static final String COMMAND_WORD = AutoSortCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "AutoSort command allows your to sort the Food item list every time you run a command.\n\n"
            + "The AutoSorting will depend on the following factor:\n"
            + "ON state or OFF state\n\n";
    public static final String USAGE =
            "autosort ON";

    public static final String OUTPUT =
            "You have turned auto-sorting on! The Food item list will now automatically sort itself.";
}

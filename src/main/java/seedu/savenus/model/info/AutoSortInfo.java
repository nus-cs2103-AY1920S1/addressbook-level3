package seedu.savenus.model.info;

import seedu.savenus.logic.commands.AutoSortCommand;

/**
 * Contains information on AutoSort command.
 */
public class AutoSortInfo {
    public static final String COMMAND_WORD = AutoSortCommand.COMMAND_WORD;

    public static final String INFORMATION =
            "Turns on and off auto sorting, based on your own custom comparator.\n"
            + "Every time you make changes to the food list. There are only two states, ON or OFF.";
    public static final String USAGE =
            "autosort ON";

    public static final String OUTPUT =
            "You have turned auto-sorting on!";
}

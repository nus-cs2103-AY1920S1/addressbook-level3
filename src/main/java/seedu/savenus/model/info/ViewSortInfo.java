package seedu.savenus.model.info;

import seedu.savenus.logic.commands.ViewSortCommand;

//@@author robytanama
/**
 * Contains information on View Sort command.
 */
public class ViewSortInfo {
    public static final String COMMAND_WORD = ViewSortCommand.COMMAND_WORD;

    public static final String INFORMATION = "View Sort command allows you to view the fields"
            + " specified for your CustomSorter.";

    public static final String USAGE = ViewSortCommand.COMMAND_WORD;

    public static final String OUTPUT = "Here are the fields specified for your CustomSorter: \n"
            + "Fields will be specified on this line.";
}

package seedu.savenus.model.info;

import seedu.savenus.logic.commands.SortCommand;

//@@author robytanama
/**
 * Contains information on Sort command.
 */
public class SortInfo {

    public static final String COMMAND_WORD = SortCommand.COMMAND_WORD;

    public static final String INFORMATION = "Sort command allows you to obtain a new sorted Food item list.\n\n"
            + "The new Food item list will depend on the following factors:\n"
            + "Field to be sorted\n"
            + "Order of sorting (Ascending / Descending)\n\n";

    public static final String USAGE = "sort name ASC";

    public static final String OUTPUT = "You have successfully sorted the food items!!";
}

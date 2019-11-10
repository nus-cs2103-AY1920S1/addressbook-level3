package seedu.savenus.model.info;

import seedu.savenus.logic.commands.DeleteCommand;

//@@author robytanama
/**
 * Contains information on Delete command.
 */
public class DeleteInfo {

    public static final String COMMAND_WORD = DeleteCommand.COMMAND_WORD;

    public static final String INFORMATION = "Delete command allows you to delete a Food item from the list.\n\n"
            + "The deleted Food item will depend on the following factor:\n"
            + "Index\n\n";

    public static final String USAGE = "delete 3";

    public static final String OUTPUT = "Food item number 3 will be deleted.";
}

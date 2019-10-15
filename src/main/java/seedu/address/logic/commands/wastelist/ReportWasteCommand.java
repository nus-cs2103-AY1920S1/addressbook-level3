package seedu.address.logic.commands.wastelist;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ReportWasteCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Provides you with a report of your past food waste.\n"
            + "Example: wlist " + COMMAND_WORD;
    // In v1.3, will incorporate start and end date.

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

}

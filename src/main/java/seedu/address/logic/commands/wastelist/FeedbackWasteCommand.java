package seedu.address.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


public class FeedbackWasteCommand extends Command {

    public static final String COMMAND_WORD = "feedback";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Gives you feedback based on your current food waste performance for the month.\n"
            + "Example: wlist " + COMMAND_WORD;

    private static final String MESSAGE_SUCCESS = "Successfully displayed feedback results";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        /*
        //Give feedback based on current month's waste
        model.
        1. Get the current waste list
        2. Compute current food waste, predicted food waste
        3. Compare with last month's statistics, remark if there is an increase or decrease in food waste.
         */
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

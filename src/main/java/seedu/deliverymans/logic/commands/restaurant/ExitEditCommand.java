package seedu.deliverymans.logic.commands.restaurant;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;

/**
 * Exits editing mode for a restaurant
 */
public class ExitEditCommand extends Command {
    public static final String COMMAND_WORD = "exitedit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exited Editing Mode";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, Context.RESTAURANT);
    }
}

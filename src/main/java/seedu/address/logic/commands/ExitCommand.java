package seedu.address.logic.commands;

import seedu.address.model.ItemModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "ELISee you again!";

    @Override
    public CommandResult execute(ItemModel model) {
        model.forceOffPriorityMode();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}

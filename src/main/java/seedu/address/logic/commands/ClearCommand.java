package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "SML data has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // do something here
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

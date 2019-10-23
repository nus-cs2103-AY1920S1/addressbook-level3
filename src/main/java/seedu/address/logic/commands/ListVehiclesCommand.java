package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all incidents in the address book to the user.
 */
public class ListVehiclesCommand extends Command {

    public static final String COMMAND_WORD = "vehicles";

    public static final String MESSAGE_SUCCESS = "Listed all vehicles";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

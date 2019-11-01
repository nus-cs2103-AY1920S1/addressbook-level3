package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;

/**
 * Lists all the deliverymen based on their current statuses.
 */
public class ListStatusCommand extends Command {
    public static final String COMMAND_WORD = "statuses";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the 3 status lists of deliverymen "
            + "in the order: AVAILABLE, DELIVERING, UNAVAILABLE from left to right.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed the available, delivering and unavailable deliverymen";

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);

        //model.;

        return new CommandResult(MESSAGE_SUCCESS, ListStatusCommand.class);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListStatusCommand); // instanceof handles nulls
    }
}

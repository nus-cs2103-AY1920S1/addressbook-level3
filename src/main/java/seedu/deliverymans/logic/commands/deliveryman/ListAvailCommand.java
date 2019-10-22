package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Lists all the available deliverymen.
 */
public class ListAvailCommand extends Command {

    public static final String COMMAND_WORD = "lista";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the deliverymen who are currently available.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LIST_AVAIL_SUCCESS = "Listed all currently available deliverymen";

    public ListAvailCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.listAvailableDeliverymen();
        return new CommandResult(MESSAGE_LIST_AVAIL_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAvailCommand); // instanceof handles nulls
    }
}

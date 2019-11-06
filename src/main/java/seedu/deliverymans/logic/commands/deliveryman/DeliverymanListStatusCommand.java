package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Lists the deliverymen sorted by their current statuses.
 */
public class DeliverymanListStatusCommand extends Command {

    public static final String COMMAND_WORD = "lists";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the 3 lists of deliverymen sorted according to respective statuses.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LIST_AVAIL_SUCCESS = "Listed all status sorted deliverymen.\n"
            + "Click on the buttons to view the desired list.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_LIST_AVAIL_SUCCESS, DeliverymanListStatusCommand.class);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanListStatusCommand); // instanceof handles nulls
    }
}

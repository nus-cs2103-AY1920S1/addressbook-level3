package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;

/**
 * (to be added)
 */
public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the statistics of deliverymen statuses.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LIST_SUCCESS = "Displayed statistics of deliverymen statuses";

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_LIST_SUCCESS, Context.DELIVERYMENSTATISTICS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetStatisticsCommand); // instanceof handles nulls
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.model.Model;

/**
 * mark a appointment's status as MISSED for a patient.
 */
public class MissAppCommand extends NonActionableCommand {
    public static final String COMMAND_WORD = "missappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all the missed appointment before today "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    public MissAppCommand() {

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateToMissedEventList();
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

}

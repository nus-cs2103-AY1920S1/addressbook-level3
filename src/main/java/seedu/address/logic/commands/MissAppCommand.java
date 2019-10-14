package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import static java.util.Objects.requireNonNull;

/**
 * mark a appointment's status as MISSED for a patient.
 */
public class MissAppCommand extends Command {
    public static final String COMMAND_WORD = "missAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all the missed appointment before today "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD;

    public MissAppCommand(){

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateToMissedEventList();
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

}

package seedu.travezy.itinerary.commands;

import seedu.travezy.itinerary.model.Model;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;

/**
 * sort the event list in the itinerary based on the condition given.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Sort based on the following format: sort by/[alphabetical | chronological | completion]."
            + "Example: sort by/alphabetical";

    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "TravEzy has helped sorted out your life!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}

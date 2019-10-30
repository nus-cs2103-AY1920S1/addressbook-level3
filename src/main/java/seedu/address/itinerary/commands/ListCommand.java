package seedu.address.itinerary.commands;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Give a list of the current events in the itinerary.
 */
public class ListCommand extends Command<Model> {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all the event that you "
            + "currently have in your itinerary.";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Here are all the events that you currently have in your itinerary!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}

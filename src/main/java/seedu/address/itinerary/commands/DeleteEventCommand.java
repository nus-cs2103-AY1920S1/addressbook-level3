package seedu.address.itinerary.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Event;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * delete event command for itinerary.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your event has been deleted successfully. Yay! :^)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the event based on the specified index.\n"
            + "Format: delete [index]";

    private final Index index;

    public DeleteEventCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> modelList = model.getFilteredEventList();
        int numIndex = index.getZeroBased();

        if (modelList.size() > numIndex) {

            model.deleteEvent(index.getOneBased());
            return new CommandResult(MESSAGE_SUCCESS, false, false);
        } else {
            throw new CommandException("Easy pal! You seemed to input an invalid index.\n"
                    + "This event does not exist! o(╥﹏╥)o");
        }
    }
}

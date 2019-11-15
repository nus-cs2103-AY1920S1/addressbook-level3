package seedu.address.itinerary.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Event;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Delete event command for itinerary.
 */
public class DeleteEventCommand extends Command<Model> {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your event has been deleted successfully. Yay! :^)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the event based on the specified index.\n"
            + "Format: delete INDEX";

    private final Index index;

    public DeleteEventCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> modelList = model.getSortedEventList();
        int numIndex = index.getZeroBased();

        if (modelList.size() > numIndex) {
            Event event = modelList.get(numIndex);

            model.deleteEvent(event);
            return new CommandResult(MESSAGE_SUCCESS, false, false);
        } else {
            throw new CommandException("Easy pal! You seemed to input an invalid index.\n"
                    + "This event does not exist! o(╥﹏╥)o");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && index.equals(((DeleteEventCommand) other).index)); // state check
    }
}

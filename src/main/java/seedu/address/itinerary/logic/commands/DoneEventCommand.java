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
 * Check and mark as done for the specified event in the itinerary.
 */
public class DoneEventCommand extends Command<Model> {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your event has been marked done successfully. Yay! :^)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the event of the specified event as done.\n"
            + "Format: done INDEX";
    private final Index index;

    /**
     * @param index of the event in the filtered event list to check as done
     */
    public DoneEventCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> modelList = model.getSortedEventList();
        int numIndex = index.getZeroBased();

        if (modelList.size() > numIndex && !modelList.get(numIndex).getIsDone()) {
            Event event = modelList.get(numIndex);

            event.markIsDone();
            model.doneEvent(event, event);
            model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
            return new CommandResult(MESSAGE_SUCCESS, false, false);
        } else {
            throw new CommandException("Easy pal! You seemed to input an invalid index.\nEither "
                    + "this event does not exist or has already been marked done! O_o");
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneEventCommand // instanceof handles nulls
                && index.equals(((DoneEventCommand) other).index)); // state check
    }
}

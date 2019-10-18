package seedu.address.itinerary.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.Event.Event;
import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class DoneEventCommand extends Command {
    /** {@code Predicate} that always evaluate to true */
    public Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n" +
            "Your event has been marked done successfully. Yay! :^)";
    public static final String MESSAGE_USAGE = "";
    private final Index index;

    public DoneEventCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Event> modelList = model.getFilteredEventList();
        int numIndex = index.getZeroBased();

        if (modelList.size() > numIndex && modelList.get(numIndex).getIsDone() == false) {
            Event event = modelList.get(numIndex);

            event.markIsDone();
            model.doneEvent(event, event);
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            return new CommandResult(MESSAGE_SUCCESS, false, false);
        } else {
            throw new CommandException("Easy pal! You seemed to input an invalid index.\nEither "
                    + "this event does not exist or has already been marked done! O_o");
        }
    }
}

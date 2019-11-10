package seedu.address.itinerary.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Event;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * add event command for itinerary.
 */
public class AddEventCommand extends Command<Model> {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the itinerary, "
            + "based on the following format:\nadd title/[title] date/ [date] time/[time] l/[location] d/[desc]\n"
            + "NOTE: Title, date and time are compulsory the rest can be left as empty fields. \n"
            + "Please remember to also change the priority of your event using the dropdown box";

    public static final String MESSAGE_DUPLICATE_EVENT = "There is another event which is happening at the same "
            + "date and time.\n"
            + "Please input an event that has a different timing instead. ^( '-' )^";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your event has been successfully added! HAND, TravEzy! :D";

    private final Event event;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasEvent(event)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(event);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && event.equals(((AddEventCommand) other).event));
    }
}

package seedu.address.itinerary.commands;

import seedu.address.itinerary.model.Event.Event;
import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import static java.util.Objects.requireNonNull;

/**
 * add event command for itinerary.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the itinerary, "
            + "based on the following format:\nadd title/[title] date/ [date] time/[time] l/[location] d/[desc]\n"
            + "NOTE: Only title is compulsory the rest can be left as empty fields.";

    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n" +
            "Your event has been successfully added! HAND, TravEzy! :D";

    private final Event event;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        this.event = event;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addEvent(event);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}


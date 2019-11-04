package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.EventBook;
import seedu.address.model.Model;

/**
 * Clears all entries in the EventsBook.
 */
public class ClearEventsCommand extends Command {

    public static final String COMMAND_WORD = "clear_ev";
    public static final String MESSAGE_SUCCESS = "EventBook has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEventBook(new EventBook());
        return new CommandResult(MESSAGE_SUCCESS, "Event");
    }
}

package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Clears all entries in the EventBook.
 */
public class ClearEventsCommand extends Command {

    public static final String COMMAND_WORD = "clear_ev";
    public static final String MESSAGE_SUCCESS = "EventBook has been cleared!";
    public static final String MESSAGE_WRONG_TAB = "Current Window does not have an Event List\n" +
            "Note: Event Commands only works on either the Main or Schedule or Statistics Tab.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(MESSAGE_WRONG_TAB);
        }

        model.setEventBook(new EventBook());
        return new CommandResult(MESSAGE_SUCCESS, "Event");
    }
}

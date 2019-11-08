package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        model.setEventBook(new EventBook());
        return new CommandResult(MESSAGE_SUCCESS, "Event");
    }
}

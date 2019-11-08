package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Lists all events in the event book to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "list_ev";

    public static final String MESSAGE_SUCCESS = "Listed all events.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.updateFilteredScheduledEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, "List");
    }
}

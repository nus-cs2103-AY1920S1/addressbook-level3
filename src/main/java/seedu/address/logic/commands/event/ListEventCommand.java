package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

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
    public static final String MESSAGE_WRONG_TAB = "Current Window does not have an Event List\n" +
            "Note: Event Commands only works on either the Main or Schedule or Statistics Tab.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(MESSAGE_WRONG_TAB);
        }

        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.updateFilteredScheduledEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, "List");
    }
}

package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "find_ev_name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " party";

    public static final String MESSAGE_WRONG_TAB = "Current Window does not have an Event List\n" +
            "Note: Event Commands only works on either the Main or Schedule or Statistics Tab.";

    private final EventNameContainsKeywordsPredicate predicate;

    public FindEventCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(MESSAGE_WRONG_TAB);
        }

        ObservableList<Event> currentEventList = MainWindow.getUpdatedCurrentEventList(model, predicate);

        String resultMessage = currentEventList.size() == 1
                ? Messages.MESSAGE_EVENT_LISTED_OVERVIEW
                : Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;

        return new CommandResult(
                String.format(resultMessage, currentEventList.size()), "List");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}

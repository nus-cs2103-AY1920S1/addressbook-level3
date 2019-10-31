package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "find_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " party";

    private final EventNameContainsKeywordsPredicate predicate;

    public FindEventCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Event> currentEventList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            model.updateFilteredEventList(predicate);
            currentEventList = model.getFilteredEventList();
        } else {
            model.updateFilteredScheduledEventList(predicate);
            currentEventList = model.getFilteredScheduledEventList();
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, currentEventList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}

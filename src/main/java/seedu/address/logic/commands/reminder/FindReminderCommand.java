package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.reminder.ReminderContainsKeywordsPredicate;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindReminderCommand extends Command {
    public static final String COMMAND_WORD = "find_reminder_by_description";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose description contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2100 CS2103T";

    private final ReminderContainsKeywordsPredicate predicate;

    public FindReminderCommand(ReminderContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReminderList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_REMINDER_LISTED_OVERVIEW, model.getFilteredReminderList().size()),
                false, false, false, false, false,
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindReminderCommand // instanceof handles nulls
                && predicate.equals(((FindReminderCommand) other).predicate)); // state check
    }
}

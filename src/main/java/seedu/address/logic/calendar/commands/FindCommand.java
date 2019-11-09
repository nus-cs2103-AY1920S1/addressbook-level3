package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.task.TaskTitleContainsKeywordsPredicate;

/**
 * Finds and lists all <code>Task</code> in Modulo's calendar whose <code>TaskTitle</code> contains any of the
 * argument keywords. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment tutorial math";

    private final TaskTitleContainsKeywordsPredicate predicate;

    public FindCommand(TaskTitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        requireNonNull(calendarModel);
        calendarModel.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, calendarModel.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.billboard.model.Model;
import seedu.billboard.model.recurrence.RecurrenceList;

/**
 * Lists all existing archive names to the user.
 */
public class ListRecurrenceCommand extends RecurrenceCommand {

    public static final String MESSAGE_NO_RECURRENCES = "There are no existing recurrences";

    public static final String MESSAGE_EXISTING_RECURRENCES = "Here are the existing recurrence(s):\n";

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        RecurrenceList recurrences = model.getRecurrences();
        if (recurrences.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECURRENCES);
        }

        return new CommandResult(MESSAGE_EXISTING_RECURRENCES + recurrences.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListRecurrenceCommand); // instanceof handles nulls
    }
}

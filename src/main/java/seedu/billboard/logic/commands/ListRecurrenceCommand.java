package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.billboard.model.Model;
import seedu.billboard.model.recurrence.RecurrenceList;

/**
 * Lists all existing archive names to the user.
 */
public class ListRecurrenceCommand extends RecurrenceCommand {

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        RecurrenceList recurrences = model.getRecurrences();
        String feedback = recurrences.toString();
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListRecurrenceCommand); // instanceof handles nulls
    }
}

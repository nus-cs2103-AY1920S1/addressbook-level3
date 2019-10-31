package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.billboard.model.Model;
import seedu.billboard.model.recurrence.Recurrence;

/**
 * Lists all existing archive names to the user.
 */
public class ListRecurrenceCommand extends RecurrenceCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_NO_RECURRENCES = "There are no existing recurrences";

    public static final String MESSAGE_EXISTING_RECURRENCES = "Here are the existing recurrence(s):\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Recurrence> recurrences = model.getRecurrences();
        String feedback;
        if (recurrences.size() == 0) {
            feedback = MESSAGE_NO_RECURRENCES;
        } else {
            feedback = MESSAGE_EXISTING_RECURRENCES;
            for (int i = 0; i < recurrences.size(); i++) {
                feedback += "[" + recurrences.get(i) + "]";
                if (i != recurrences.size() - 1) {
                    feedback += ",\n";
                }
            }
        }
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListRecurrenceCommand); // instanceof handles nulls
    }
}

package seedu.address.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
/**
 * Lists all reminders in the address book to the user.
 */
public class ListAllRemindersCommand extends Command {

    public static final String COMMAND_WORD = "listAllReminders";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReminders(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

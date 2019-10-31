package seedu.address.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ACTIVE_REMINDERS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListActiveRemindersCommand extends Command {

    public static final String COMMAND_WORD = "listActiveReminders";

    public static final String MESSAGE_SUCCESS = "Listed active reminders";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReminders(PREDICATE_SHOW_ACTIVE_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

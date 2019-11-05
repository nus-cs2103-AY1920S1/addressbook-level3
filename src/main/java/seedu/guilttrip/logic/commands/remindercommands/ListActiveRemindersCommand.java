package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ACTIVE_REMINDERS;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;

/**
 * List only reminders that are active;
 */
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

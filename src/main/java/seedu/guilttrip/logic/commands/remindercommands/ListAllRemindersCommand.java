package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
/**
 * Lists all reminders in GuiltTrip to the user.
 */
public class ListAllRemindersCommand extends Command {

    public static final String COMMAND_WORD = "listAllReminders";
    public static final String ONE_LINER_DESC = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all reminders";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReminders(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS, true, "reminder");
    }
}

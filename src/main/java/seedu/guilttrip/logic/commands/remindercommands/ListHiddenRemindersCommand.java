package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_HIDDEN_REMINDERS;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;

public class ListHiddenRemindersCommand extends Command {

    public static final String COMMAND_WORD = "listHiddenReminders";

    public static final String MESSAGE_SUCCESS = "Listed hidden reminders";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReminders(PREDICATE_SHOW_HIDDEN_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

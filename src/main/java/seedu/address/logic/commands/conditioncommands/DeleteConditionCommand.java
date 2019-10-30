package seedu.address.logic.commands.conditioncommands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.reminders.conditions.Condition;

/**
 * Delete condition using its displayed index.
 */
public class DeleteConditionCommand extends Command {

    public static final String COMMAND_WORD = "deleteCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the condition identified by the index number used in the displayed condition list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Condition    : %1$s";
    private Index targetIndex;
    public DeleteConditionCommand(Index index) {
        this.targetIndex = index;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Condition> lastShownList = model.getFilteredConditions();
        Condition conditionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCondition(conditionToDelete);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, conditionToDelete));
        commandResult.showConditionPanel();
        return commandResult;
    }
}

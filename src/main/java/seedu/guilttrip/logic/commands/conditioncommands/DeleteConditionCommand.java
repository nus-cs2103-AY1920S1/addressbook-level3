package seedu.guilttrip.logic.commands.conditioncommands;

import java.util.List;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.conditions.Condition;

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

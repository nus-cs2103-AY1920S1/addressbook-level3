package seedu.guilttrip.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;

/**
 * Lists conditions to the user.
 */
public class ShowConditionListCommand extends Command {
    public static final String COMMAND_WORD = "listConditions";

    public static final String MESSAGE_SUCCESS = "List all conditions";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.showConditionPanel();
        return commandResult;
    }
}

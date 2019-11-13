package budgetbuddy.logic.commands.scriptcommands;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;

/**
 * Lists scripts.
 */
public class ScriptListCommand extends Command {
    public static final String COMMAND_WORD = "script list";

    public static final String MESSAGE_SUCCESS = "Scripts listed.";

    @Override
    protected CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, CommandCategory.SCRIPT);
    }
}

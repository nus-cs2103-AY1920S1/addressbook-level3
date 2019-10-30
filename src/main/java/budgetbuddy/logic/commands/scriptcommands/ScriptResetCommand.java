package budgetbuddy.logic.commands.scriptcommands;

import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;

/**
 * Resets the script environment.
 */
public class ScriptResetCommand extends ScriptCommand {
    public static final String COMMAND_WORD = "script reset";

    public static final String MESSAGE_SCRIPT_RESET = "Script environment reset.";

    @Override
    public CommandResult execute(Model model, ScriptEngine scriptEngine) {
        scriptEngine.resetEnvironment();
        return new CommandResult(MESSAGE_SCRIPT_RESET, CommandCategory.SCRIPT);
    }
}

package budgetbuddy.logic.commands.scriptcommands;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;

/**
 * Acts as a base class for commands that use the script manager.
 */
public abstract class ScriptCommand extends Command {
    @Override
    public abstract CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException;

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        return execute(model, null);
    }
}

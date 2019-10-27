package budgetbuddy.logic.commands;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@link Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    protected abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@link Model} which the command should operate on.
     * @param scriptEngine {@link ScriptEngine} which the command should use to evaluate any scripts.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException {
        return execute(model);
    }
}

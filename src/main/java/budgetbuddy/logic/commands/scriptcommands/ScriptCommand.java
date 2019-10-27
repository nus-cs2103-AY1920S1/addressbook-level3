package budgetbuddy.logic.commands.scriptcommands;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.Model;

/**
 * Acts as a base class for commands that run scripts.
 */
public abstract class ScriptCommand extends Command {
    public static final String MESSAGE_NO_RESULT = "Script succeeded with no result.";
    public static final String MESSAGE_SCRIPT_EXCEPTION = "Exception thrown during script evaluation\n%1$s";

    @Override
    public abstract CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException;

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        return execute(model, null);
    }

    /**
     * Helper method to run a script and return a {@link CommandResult} with the result of the script.
     *
     * @param scriptEngine the script engine
     * @param script the script
     * @param argv the arguments to pass to the script
     * @return the command result
     * @throws CommandException if an error occurs while running the script
     */
    protected static CommandResult runScript(ScriptEngine scriptEngine, String script, Object... argv)
            throws CommandException {
        try {
            Object result = scriptEngine.evaluateScript(script, argv);
            if (result == null) {
                return new CommandResult(MESSAGE_NO_RESULT, CommandCategory.SCRIPT);
            } else {
                return new CommandResult(result.toString(), CommandCategory.SCRIPT);
            }
        } catch (ScriptException se) {
            throw new CommandException(String.format(MESSAGE_SCRIPT_EXCEPTION, se.getMessage()), se);
        }
    }
}

package budgetbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptManager;
import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.Model;

/**
 * Evaluates a script. The script has access to the full model.
 */
public class EvalCommand extends Command {
    public static final String COMMAND_WORD = "eval";

    public static final String MESSAGE_NO_RESULT = "Script succeeded with no result.";
    public static final String MESSAGE_SCRIPT_EXCEPTION = "Exception thrown during script evaluation\n%1$s";

    private final String script;

    public EvalCommand(String script) {
        this.script = script;
    }

    @Override
    public CommandResult execute(Model model, ScriptManager scriptManager) throws CommandException {
        requireNonNull(scriptManager);
        try {
            Object result = scriptManager.evaluateScript(script);
            if (result == null) {
                return new CommandResult(MESSAGE_NO_RESULT, null);
            } else {
                return new CommandResult(result.toString(), null);
            }
        } catch (ScriptException se) {
            throw new CommandException(String.format(MESSAGE_SCRIPT_EXCEPTION, se.getMessage()), se);
        }
    }

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        return execute(model, null);
    }
}

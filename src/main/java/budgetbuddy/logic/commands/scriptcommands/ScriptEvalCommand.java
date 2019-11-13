package budgetbuddy.logic.commands.scriptcommands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;

/**
 * Evaluates a script. The script has access to the full model.
 */
public class ScriptEvalCommand extends ScriptCommand {
    public static final String COMMAND_WORD = "script eval";

    private final String script;

    public ScriptEvalCommand(String script) {
        requireNonNull(script);
        this.script = script;
    }

    @Override
    public CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException {
        requireNonNull(scriptEngine);
        return runScript(scriptEngine, script);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptEvalCommand that = (ScriptEvalCommand) o;
        return script.equals(that.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(script);
    }
}

package budgetbuddy.logic.commands.scriptcommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_NO_SUCH_SCRIPT;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;

/**
 * Runs a script from the script library.
 */
public class ScriptRunCommand extends ScriptCommand {
    public static final String COMMAND_WORD = "script run";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Run a stored script.\n"
            + "Usage: " + COMMAND_WORD + " <script name> [<argument>]\n"
            + "Example: " + COMMAND_WORD + " delete-everything This entire string is passed to the script.";

    private final ScriptName scriptName;
    private final String arguments;

    public ScriptRunCommand(ScriptName scriptName, String arguments) {
        requireAllNonNull(scriptName, arguments);
        this.scriptName = scriptName;
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute(Model model, ScriptEngine scriptEngine) throws CommandException {
        ScriptLibrary scriptLibrary = model.getScriptLibrary();
        Script s = scriptLibrary.getScript(scriptName);
        if (s == null) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_SCRIPT, scriptName));
        } else {
            return runScript(scriptEngine, s.getCode(), arguments);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScriptRunCommand that = (ScriptRunCommand) o;
        return scriptName.equals(that.scriptName) && arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptName, arguments);
    }
}

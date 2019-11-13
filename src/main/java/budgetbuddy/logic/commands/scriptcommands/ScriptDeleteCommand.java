package budgetbuddy.logic.commands.scriptcommands;

import static budgetbuddy.commons.core.Messages.MESSAGE_NO_SUCH_SCRIPT;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.script.ScriptName;

/**
 * Deletes a script from the script library.
 */
public class ScriptDeleteCommand extends Command {
    public static final String COMMAND_WORD = "script delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a script from the library.\n"
            + "Usage: " + COMMAND_WORD + " <script name>\n"
            + "Example: " + COMMAND_WORD + " delete-everything";

    public static final String MESSAGE_SCRIPT_DELETED = "Script %1$s deleted.";

    private final ScriptName scriptName;

    public ScriptDeleteCommand(ScriptName scriptName) {
        requireNonNull(scriptName);
        this.scriptName = scriptName;
    }

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        ScriptLibrary scriptLibrary = model.getScriptLibrary();
        boolean removed = scriptLibrary.removeScript(scriptName);
        if (removed) {
            return new CommandResult(String.format(MESSAGE_SCRIPT_DELETED, scriptName),
                    CommandCategory.SCRIPT);
        } else {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_SCRIPT, scriptName));
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
        ScriptDeleteCommand that = (ScriptDeleteCommand) o;
        return scriptName.equals(that.scriptName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptName);
    }
}

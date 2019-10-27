package budgetbuddy.logic.commands.scriptcommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;

/**
 * Adds a script to, or updates a script in, the script library.
 */
public class ScriptAddCommand extends Command {
    public static final String COMMAND_WORD = "script add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a script to the script library.\n"
            + "Usage: " + COMMAND_WORD + " <script name> [d/<description>] [p/<file path> | s/<source>]\n"
            + "Example: " + COMMAND_WORD + " delete-everything d/Deletes everything. s/\"Just kidding!\";";

    public static final String MESSAGE_SCRIPT_UPDATED = "Script %1$s updated.";
    public static final String MESSAGE_SCRIPT_ADDED = "New script %1$s added.";

    public static final String MESSAGE_INVALID_PATH = "Invalid path.";

    private final ScriptName scriptName;
    private final Description description;
    private final Path scriptPath;
    private final String scriptSource;

    public ScriptAddCommand(ScriptName name, Description description, Path scriptPath) {
        requireAllNonNull(name, description);
        this.scriptName = name;
        this.description = description;
        this.scriptPath = scriptPath;
        this.scriptSource = null;
    }

    public ScriptAddCommand(ScriptName name, Description description, String scriptSource) {
        requireAllNonNull(name, description);
        this.scriptName = name;
        this.description = description;
        this.scriptSource = scriptSource;
        this.scriptPath = null;
    }

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        ScriptLibrary library = model.getScriptLibrary();

        if (scriptSource != null) {
            Script newScript = new Script(scriptName, description, scriptSource);
            boolean updated = library.addScript(newScript);
            return new CommandResult(
                    String.format(updated ? MESSAGE_SCRIPT_UPDATED : MESSAGE_SCRIPT_ADDED, scriptName),
                    CommandCategory.SCRIPT
            );
        } else {
            // TODO
            return new CommandResult("TODO FIXME", CommandCategory.SCRIPT);
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
        ScriptAddCommand that = (ScriptAddCommand) o;
        return scriptName.equals(that.scriptName)
                && description.equals(that.description)
                && Objects.equals(scriptPath, that.scriptPath)
                && Objects.equals(scriptSource, that.scriptSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptName, description, scriptPath, scriptSource);
    }
}

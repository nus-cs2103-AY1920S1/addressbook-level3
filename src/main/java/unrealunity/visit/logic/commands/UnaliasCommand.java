package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;

import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;

/**
 * Removes an alias.
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_ALIAS_FAILED = "No existing alias named: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": removes an alias. "
            + "Parameters: "
            + "ALIAS_NAME "
            + "Example: " + COMMAND_WORD + " "
            + "h";

    private final String alias;

    /**
     * Creates an UnaliasCommand to remove an alias.
     */
    public UnaliasCommand(String alias) {
        requireNonNull(alias);
        this.alias = alias.trim().toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.removeAlias(alias)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
        } else {
            throw new CommandException(String.format(MESSAGE_ALIAS_FAILED, alias));
        }
    }
}

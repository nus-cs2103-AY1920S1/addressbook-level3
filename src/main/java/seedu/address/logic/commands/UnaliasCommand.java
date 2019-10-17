package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNALIAS_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Removes an alias.
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_ALIAS_FAILED = "No existing alias named: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": removes an alias. "
            + "Parameters: "
            + PREFIX_UNALIAS_NAME + "ALIAS_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_UNALIAS_NAME + "h";

    private final String alias;

    /**
     * Creates an UnaliasCommand to remove an alias.
     */
    public UnaliasCommand(String alias) {
        requireNonNull(alias);
        this.alias = alias.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getUserPrefs().getAliasTable().removeAlias(alias)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
        } else {
            throw new CommandException(String.format(MESSAGE_ALIAS_FAILED, alias));
        }
    }
}

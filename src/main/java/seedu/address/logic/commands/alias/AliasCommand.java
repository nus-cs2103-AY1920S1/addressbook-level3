package seedu.address.logic.commands.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Create an alias for common user input.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a shortcut for commonly used Commands.\n"
            + "Parameters: " + PREFIX_ALIAS_ALIAS_NAME
            + " <alias name>  " + PREFIX_ALIAS_ALIAS_INPUT + " <input string> \n"
            + "Example: alias " + PREFIX_ALIAS_ALIAS_NAME
            + " findAnimal " + PREFIX_ALIAS_ALIAS_INPUT
            + " find rat rats mouse mice cow cows ox oxen tiger tigers";

    public static final String MESSAGE_SUCCESS = "Alias created: %1$s";

    public static final String MESSAGE_RESERVED_NAME =
            "%1$s is a reserved command name and cannot be used for an alias name";

    public static final String MESSAGE_RECURSIVE_WARNING =
            "This alias is not allowed because it may possibly be recursive";

    private Alias toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AliasCommand(Alias alias) {
        requireNonNull(alias);
        this.toAdd = alias;
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        // if command_word is reserved
        String aliasName = toAdd.getAliasName();
        if (model.getUserPrefs().aliasNameIsReserved(toAdd)) {
            throw new CommandException(String.format(MESSAGE_RESERVED_NAME, aliasName));
        }

        // if recursive
        String commandWord = toAdd.getCommandWord();
        if (commandWord.equalsIgnoreCase(aliasName) || model.getUserPrefs().aliasCommandWordIsAlias(toAdd)) {
            throw new CommandException(MESSAGE_RECURSIVE_WARNING);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addUserAlias(toAdd);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd.getAliasName()));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof AliasCommand // instanceof handles nulls
                && this.toAdd.equals(((AliasCommand) obj).toAdd));
    }
}

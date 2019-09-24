package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Create a shortcut for another Command.
 */
public class AliasCommand extends Command {


    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a shortcut for commonly used Commands.\n"
            + "Parameters: ALIAS_NAME COMMAND_WORD [ARGUMENTS]\n"
            + "Example: findAnimal find rat rats mouse mice cow cows ox oxen tiger tigers";

    public static final String MESSAGE_SUCCESS = "Alias created: %1$s";

    public static final String MESSAGE_RESERVED_NAME =
            "%1$s is a reserved command name and cannot be used for an alias name";

    public static final String MESSAGE_RECURSIVE_WARNING =
            "This alias is not allowed because it may possibly be recursive";

    //public static final String MESSAGE_REFERS_TO_ALIAS =
    //        "%1$s references another alias (%2$s) and may not be used";

    private Alias toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AliasCommand(Alias alias) {
        requireNonNull(alias);
        this.toAdd = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if command_word is reserved
        String aliasName = toAdd.getAliasName();
        if (model.aliasNameIsReserved(aliasName)) {
            throw new CommandException(String.format(MESSAGE_RESERVED_NAME, aliasName));
        }

        // if recursive
        String commandWord = toAdd.getInput().stripLeading().split("\\s+")[0];
        if (commandWord.equalsIgnoreCase(aliasName)
                ||commandWord.equalsIgnoreCase(COMMAND_WORD)
                ||model.aliasCommandWordIsAlias(commandWord)) {
            throw new CommandException(MESSAGE_RECURSIVE_WARNING);
        }

        model.addUserAlias(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getAliasName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }
}

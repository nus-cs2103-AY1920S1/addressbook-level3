package seedu.moolah.logic.commands.alias;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.commons.exceptions.RecursiveAliasException;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.ui.alias.AliasListPanel;

/**
 * Create an alias for common user input.
 */
public class AddAliasCommand extends UndoableCommand {


    public static final String COMMAND_WORD = GenericCommandWord.ADD + CommandGroup.ALIAS;
    public static final String COMMAND_DESCRIPTION = "Create alias %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a shortcut for commonly used Commands.\n"
            + "Parameters: \n"
            + PREFIX_ALIAS_ALIAS_NAME + "<" + PREFIX_ALIAS_ALIAS_NAME.getDescriptionOfArgument() + ">" + "\n"
            + PREFIX_ALIAS_ALIAS_INPUT + "<" + PREFIX_ALIAS_ALIAS_INPUT.getDescriptionOfArgument() + ">" + "\n"
            + "Format: \n"
            + COMMAND_WORD + " "
            + PREFIX_ALIAS_ALIAS_NAME + "<" + PREFIX_ALIAS_ALIAS_NAME.getDescriptionOfArgument() + ">" + " "
            + PREFIX_ALIAS_ALIAS_INPUT + "<" + PREFIX_ALIAS_ALIAS_INPUT.getDescriptionOfArgument() + ">" + "\n"
            + "Example: \n"
            + COMMAND_WORD + " " + PREFIX_ALIAS_ALIAS_NAME + " "
            + "findAnimal " + PREFIX_ALIAS_ALIAS_INPUT + " "
            + " find rat rats mouse mice cow cows ox oxen tiger tigers";

    public static final String MESSAGE_SUCCESS = "Alias created:\n %1$s";

    public static final String MESSAGE_RESERVED_NAME =
            "%1$s is a reserved command name and cannot be used for an alias name";

    public static final String MESSAGE_RECURSIVE_WARNING =
            "This alias is not allowed because it may possibly be recursive";

    private Alias toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddAliasCommand(Alias alias) {
        requireNonNull(alias);
        this.toAdd = alias;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, toAdd.getAliasName());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.getAliasMappings().addAlias(toAdd).validate();
        } catch (IllegalValueException e) {
            throw new CommandException(String.format(MESSAGE_RESERVED_NAME, toAdd.getAliasName()));
        } catch (RecursiveAliasException e) {
            throw new CommandException(MESSAGE_RECURSIVE_WARNING);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addUserAlias(toAdd);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd.getAliasName()), AliasListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof AddAliasCommand // instanceof handles nulls
                && this.toAdd.equals(((AddAliasCommand) obj).toAdd));
    }

}

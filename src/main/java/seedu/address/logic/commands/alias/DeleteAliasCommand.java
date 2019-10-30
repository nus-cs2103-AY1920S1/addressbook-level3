package seedu.address.logic.commands.alias;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.alias.AliasPanel;

/**
 * Create an alias for common user input.
 */
public class DeleteAliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.DELETE + CommandGroup.ALIAS;
    public static final String COMMAND_DESCRIPTION = "Delete alias %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete an alias with the alias name.\n"
            + "Parameters: <alias name>\n"
            + "Example: deletealias findCat";

    public static final String MESSAGE_SUCCESS = "Alias deleted: %1$s";

    public static final String MESSAGE_FAILURE = "There is no Alias with the following name: \"%s\"";

    private String aliasName;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public DeleteAliasCommand(String aliasName) {
        requireNonNull(aliasName);
        this.aliasName = aliasName;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, aliasName);
    }

    @Override
    protected void validate(Model model) throws CommandException {
        // no validation needed
        if (!model.aliasWithNameExists(aliasName)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, aliasName));
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.removeAliasWithName(aliasName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, aliasName), AliasPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof DeleteAliasCommand // instanceof handles nulls
                && this.aliasName.equals(((DeleteAliasCommand) obj).aliasName));
    }
}

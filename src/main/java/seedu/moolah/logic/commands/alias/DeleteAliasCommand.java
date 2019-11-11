package seedu.moolah.logic.commands.alias;

import static java.util.Objects.requireNonNull;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.ui.alias.AliasListPanel;

/**
 * Create an alias for common user input.
 */
public class DeleteAliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.DELETE + CommandGroup.ALIAS;
    public static final String COMMAND_DESCRIPTION = "Delete alias %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete an alias with the alias name.\n"
            + "Parameters: <alias name>\n"
            + "Example: deletealias findCat";

    public static final String MESSAGE_SUCCESS = "Alias deleted:\n %1$s";

    public static final String MESSAGE_FAILURE = "There is no Alias with the following name: \"%s\"";

    private String aliasName;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public DeleteAliasCommand(String aliasName) {
        requireNonNull(aliasName);
        this.aliasName = aliasName.trim();
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, aliasName), AliasListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof DeleteAliasCommand // instanceof handles nulls
                && this.aliasName.trim().equals(((DeleteAliasCommand) obj).aliasName.trim()));
    }

}

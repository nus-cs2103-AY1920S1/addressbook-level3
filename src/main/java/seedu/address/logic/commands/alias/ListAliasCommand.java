package seedu.address.logic.commands.alias;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
//import seedu.address.ui.expense.AliasPanel;
import seedu.address.ui.alias.AliasPanel;

/**
 * Create an alias for common user input.
 */
public class ListAliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.ALIAS;
    public static final String COMMAND_DESCRIPTION = "List aliases";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all user defined alises.";

    public static final String MESSAGE_SUCCESS = "You have %d aliases.";

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        // no validation needed
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getAliasMappings().getAliases().size()),
                AliasPanel.PANEL_NAME);
    }
}

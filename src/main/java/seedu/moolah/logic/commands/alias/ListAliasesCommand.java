package seedu.moolah.logic.commands.alias;

import static java.util.Objects.requireNonNull;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.ui.alias.AliasListPanel;

/**
 * Create an alias for common user input.
 */
public class ListAliasesCommand extends Command {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.ALIAS;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all user defined alises.";

    public static final String MESSAGE_SUCCESS = "You have %d aliases.";

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getAliasMappings().getAliases().size()),
                AliasListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListAliasesCommand;
    }
}

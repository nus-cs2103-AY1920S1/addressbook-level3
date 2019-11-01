package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.MooLah;
import seedu.address.model.alias.AliasMappings;
import seedu.address.ui.expense.ExpenseListPanel;

/**
 * Clears the MooLah.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.CLEAR + "all";
    public static final String COMMAND_DESCRIPTION = "Clear MooLah";
    public static final String MESSAGE_SUCCESS = "MooLah has been reset!";

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMooLah(new MooLah());
        model.setAliasMappings(new AliasMappings());
        return new CommandResult(MESSAGE_SUCCESS, ExpenseListPanel.PANEL_NAME);
    }
}

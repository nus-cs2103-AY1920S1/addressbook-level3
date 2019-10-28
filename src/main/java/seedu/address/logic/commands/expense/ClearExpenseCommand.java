package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.MooLah;
import seedu.address.ui.expense.ExpenseListPanel;

/**
 * Clears the MooLah.
 */
public class ClearExpenseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear" + CommandGroup.EXPENSE;;
    public static final String MESSAGE_SUCCESS = "MooLah has been cleared!";

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMooLah(new MooLah());
        return new CommandResult(MESSAGE_SUCCESS, ExpenseListPanel.PANEL_NAME);
    }
}

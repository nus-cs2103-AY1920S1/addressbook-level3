package seedu.moolah.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.ui.expense.ExpenseListPanel;

/**
 * Clears the MooLah.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.CLEAR + "moolah" + CommandGroup.GENERAL;
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

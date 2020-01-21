package seedu.algobase.logic.commands.gui;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_INDEX;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.gui.WriteOnlyTabManager;

/**
 * Closes a tab in the GUI.
 */
public class CloseTabCommand extends Command {

    public static final String COMMAND_WORD = "closetab";
    public static final String SHORT_COMMAND_WORD = "ct";
    public static final String MESSAGE_SUCCESS = "Closed tab %1$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Closes a details tab in the GUI\n"
        + "Parameters:\n"
        + PREFIX_TAB_INDEX + "INDEX OF TAB (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " "
        + PREFIX_TAB_INDEX + "1\n";

    public static final String MESSAGE_INVALID_TAB_INDEX = "There is no tab at index %1$s!";

    private Index index;

    public CloseTabCommand(Index displayTabIndex) {
        requireAllNonNull(displayTabIndex);
        this.index = displayTabIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            WriteOnlyTabManager tabManager = model.getGuiState().getTabManager();
            tabManager.closeDetailsTab(index);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_TAB_INDEX, index.getOneBased()));
        }
    }
}

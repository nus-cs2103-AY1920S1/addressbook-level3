package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.gui.TabEnum;

/**
 * Switch tabs in the GUI.
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchtab";
    public static final String MESSAGE_SUCCESS = "Switched to tab %1$s!"; // TODO
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Switches the current display tab in the GUI"
        + "by the index number of the current tab.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1"; // TODO

    public static final String MESSAGE_INVALID_INDEX = "There is no tab at index %1$s!";

    private Index index = Index.fromZeroBased(0);
    private TabEnum tabType;

    public SwitchTabCommand(TabEnum tabType, Index displayTabIndex) {
        requireAllNonNull(tabType, displayTabIndex);
        this.tabType = tabType;
        this.index = displayTabIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            switch (tabType) {
            case DISPLAY:
                model.getGuiState().getTabManager().setDisplayTabPaneIndex(index);
                return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
            case DETAILS:
                model.getGuiState().getTabManager().setDetailsTabPaneIndex(index);
                return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased())); // TODO
            default:
                throw new CommandException("Invalid input!"); // TODO
            }
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
        }
    }
}

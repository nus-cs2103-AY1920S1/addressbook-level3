package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_TYPE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.gui.TabType;

/**
 * Switch tabs in the GUI.
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchtab";
    public static final String SHORT_COMMAND_WORD = "st";
    public static final String MESSAGE_SUCCESS = "Switched to [%1$s] tab [%2$s].";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Switches between Tabs in the GUI\n"
        + "Parameters:\n"
        + PREFIX_TAB_TYPE + "TYPE OF TAB (display, details) "
        + PREFIX_TAB_INDEX + "INDEX OF TAB (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " "
        + PREFIX_TAB_TYPE + "display "
        + PREFIX_TAB_INDEX + "1\n";

    public static final String MESSAGE_INVALID_TAB_TYPE = "Tab type [%1$s] does not exist.";
    public static final String MESSAGE_INVALID_TAB_INDEX = "Tab at index [%1$s] does not exist.";

    private Index index = Index.fromZeroBased(0);
    private TabType tabType;

    public SwitchTabCommand(TabType tabType, Index displayTabIndex) {
        requireAllNonNull(tabType, displayTabIndex);
        this.tabType = tabType;
        this.index = displayTabIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            switch (tabType) {
            case DISPLAY:
                model.getGuiState().getTabManager().setDisplayTabPaneIndex(index);
                return new CommandResult(
                    String.format(MESSAGE_SUCCESS, tabType.DISPLAY.getName(), index.getOneBased())
                );
            case DETAILS:
                model.getGuiState().getTabManager().setDetailsTabPaneIndex(index);
                return new CommandResult(
                    String.format(MESSAGE_SUCCESS, tabType.DETAILS.getName(), index.getOneBased())
                );
            default:
                throw new CommandException(String.format(MESSAGE_INVALID_TAB_TYPE, tabType.getName()));
            }
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_TAB_INDEX, index.getOneBased()));
        }
    }
}

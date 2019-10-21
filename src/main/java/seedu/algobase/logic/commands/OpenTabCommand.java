package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelEnum;
import seedu.algobase.model.gui.AlgoBaseTab;

/**
 * Close tabs in the GUI.
 */
public class OpenTabCommand extends Command {

    public static final String COMMAND_WORD = "opentab";
    public static final String MESSAGE_SUCCESS = "opened tab %1$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD // TODO
        + ": Switches the current display tab in the GUI"
        + "by the index number of the current tab.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_INDEX = "There is no tab at index %1$s!"; // TODO

    private Index index = Index.fromZeroBased(0);
    private ModelEnum modelEnum;

    public OpenTabCommand(ModelEnum modelEnum, Index displayTabIndex) {
        requireAllNonNull(modelEnum, displayTabIndex);
        this.modelEnum = modelEnum;
        this.index = displayTabIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            AlgoBaseTab algoBaseTab = new AlgoBaseTab(modelEnum, index);
            model.getGuiState().getTabManager().addTab(algoBaseTab);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased())); // TODO
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased())); // TODO
        }
    }
}

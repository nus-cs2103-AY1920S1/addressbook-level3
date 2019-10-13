package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

/**
 * Switch tabs in the GUI.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_SUCCESS = "Switched to tab %1$s!";
    public static final String MESSAGE_INVALID_INDEX = "There is no tab at index %1$s!";

    private Index index = Index.fromZeroBased(0);

    public SwitchCommand(Index displayTabIndex) {
        requireNonNull(displayTabIndex);
        this.index = displayTabIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.getGuiState().setDisplayTabPaneIndex(index);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
        }
    }
}

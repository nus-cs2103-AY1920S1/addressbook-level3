package seedu.algobase.logic.commands;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

/**
 * Switch tabs in the GUI.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String MESSAGE_SUCCESS = "Successfully switched tabs!";

    private int index = 0;

    public SwitchCommand(Index displayTabIndex) {
        this.index = displayTabIndex.getZeroBased();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.getGuiState().setDisplayTabPaneIndex(index);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

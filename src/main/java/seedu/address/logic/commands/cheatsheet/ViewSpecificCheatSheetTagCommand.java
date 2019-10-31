package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.SHOW;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.CheatsheetTabWindowController;

public class ViewSpecificCheatSheetTagCommand extends Command {
    public static final String COMMAND_WORD = SHOW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a cheatsheet's tag's contents.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_TAG_CONTENT_SUCCESS = "Viewing tag content: ";

    private final Index targetIndex;

    public ViewSpecificCheatSheetTagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (CheatsheetTabWindowController.getCurrCheatSheet().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_CHEATSHEET_LOADED);
        }


        if (CheatsheetTabWindowController.getCurrCheatSheet().get().getTags().size() < targetIndex.getOneBased()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_INDEX);
        }

        return new CheatSheetCommandResult(VIEW_TAG_CONTENT_SUCCESS, targetIndex.getOneBased(), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewSpecificCheatSheetTagCommand // instanceof handles nulls
                && targetIndex.equals(((ViewSpecificCheatSheetTagCommand) other).targetIndex)); // state check
    }
}

package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET;
import static seedu.address.commons.core.Messages.MESSAGE_HIT_ENTER_TO_DELETE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * Deletes a cheatsheet identified using it's displayed index from the address book.
 */
public class DeleteCheatSheetCommand extends Command {
    public static final String COMMAND_WORD = DELETE;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the cheatsheet identified by the index number used in the displayed cheatsheet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CHEATSHEET_SUCCESS = "Deleted Cheatsheet: %1$s";

    // negative marked index to prevent access
    private static int markedIndex = -1;

    private static boolean isSure = false;

    private final Index targetIndex;

    public DeleteCheatSheetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public static int getMarkedIndex() {
        return markedIndex;
    }

    public static boolean isIsSure() {
        return isSure;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CheatSheet> lastShownList = model.getFilteredCheatSheetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
        }

        CheatSheet cheatsheetToDelete = lastShownList.get(targetIndex.getZeroBased());
        CheatSheetCommandResult commandResult = new CheatSheetCommandResult("");
        if (!isSure) {
            isSure = true;
            // one prompt for index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET
                    + "\n" + cheatsheetToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        if (isSure && markedIndex == this.targetIndex.getOneBased()) {
            /* if this was marked
            this is to prevent calling delete 1 then
            calling delete 2
            user is forced to delete the same index twice in a row. */
            model.deleteCheatSheet(cheatsheetToDelete);
            isSure = false;
            markedIndex = -1; // reset to -1 to prevent wrong access
            commandResult = new CheatSheetCommandResult(String.format
                    (MESSAGE_DELETE_CHEATSHEET_SUCCESS, cheatsheetToDelete));
        }
        if (isSure) {
            // user is sure he wants to delete but changed the index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET
                    + "\n" + cheatsheetToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCheatSheetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCheatSheetCommand) other).targetIndex)); // state check
    }
}

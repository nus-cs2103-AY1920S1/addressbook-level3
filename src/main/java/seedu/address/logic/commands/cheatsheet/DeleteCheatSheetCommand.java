package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET;
import static seedu.address.commons.core.Messages.MESSAGE_CONFIRM_DELETE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandHistory;
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

    private final Index targetIndex;

    public DeleteCheatSheetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CheatSheet> lastShownList = model.getFilteredCheatSheetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
        }

        CheatSheet cheatsheetToDelete = lastShownList.get(targetIndex.getZeroBased());
        CheatSheetCommandResult commandResult = new CheatSheetCommandResult ((
                MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET
                + "\n" + cheatsheetToDelete
                + "\n" + String.format(MESSAGE_CONFIRM_DELETE, this.targetIndex.getOneBased())));

        if (CommandHistory.getLastCommand().get() instanceof DeleteCheatSheetCommand) {
            if (((DeleteCheatSheetCommand) CommandHistory.getLastCommand().get()).getTargetIndex()
                    .equals(this.targetIndex)) {
                // correct. allow delete
                int currentAmountOfCheatSheets = model.getFilteredCheatSheetList().size();

                model.deleteCheatSheet(cheatsheetToDelete);

                int newAmountOfCheatSheets = model.getFilteredCheatSheetList().size();
                // to assert that one cheatsheet got deleted
                assert(newAmountOfCheatSheets == currentAmountOfCheatSheets - 1);

                commandResult = new CheatSheetCommandResult(String.format
                        (MESSAGE_DELETE_CHEATSHEET_SUCCESS, cheatsheetToDelete));
            }
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

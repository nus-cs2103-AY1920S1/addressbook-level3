package seedu.address.logic.commands.copycommand;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Copies a phone identified using it's displayed index from the seller manager.
 */
public class CopyPhoneCommand extends Command {

    public static final String COMMAND_WORD = "copy-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the phone identified by the index number used in the displayed phone list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_PHONE_SUCCESS = "Copied phone into clipboard: %1$s";

    private final Index targetIndex;

    public CopyPhoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        List<Phone> lastShownList = model.getFilteredPhoneList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
        }

        Phone phoneToCopy = lastShownList.get(targetIndex.getZeroBased());

        String phoneString = phoneToCopy.toString();
        StringSelection stringSelection = new StringSelection(phoneString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);


        return new CommandResult(String.format(MESSAGE_COPY_PHONE_SUCCESS, phoneToCopy), UiChange.PHONE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyPhoneCommand // instanceof handles nulls
                && targetIndex.equals(((CopyPhoneCommand) other).targetIndex)); // state check
    }
}

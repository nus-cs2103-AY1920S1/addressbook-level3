package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;

/**
 * Decrypt a file identified using it's displayed index from the file book.
 */
public class RemoveFileCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the file identified by the index number from the file list. "
            + "The actual file will not be deleted.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FILE_SUCCESS = "File removed from list: %1$s";

    private final Index targetIndex;

    public RemoveFileCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<EncryptedFile> lastShownList = model.getFilteredFileList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILE_DISPLAYED_INDEX);
        }

        EncryptedFile fileToDecrypt = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFile(fileToDecrypt);
        return new CommandResult(String.format(MESSAGE_DELETE_FILE_SUCCESS, fileToDecrypt));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveFileCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveFileCommand) other).targetIndex)); // state check
    }
}


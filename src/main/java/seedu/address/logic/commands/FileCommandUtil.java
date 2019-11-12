package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FILES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileStatus;

/**
 * Utility class for file commands.
 */
public class FileCommandUtil {
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist. "
            + "File may be renamed or deleted. \nUse remove command to remove the file from the file list.";
    public static final String MESSAGE_FILE_NOT_ENCRYPTED = "File may not be encrypted or may be corrupted."
            + "\nUse remove command to remove the file from the file list.";

    /**
     * Checks if the file specified exists in the file system and updates the model.
     */
    public static void checkIfFileExists(EncryptedFile file, Model model) throws CommandException {
        if (!Files.exists(Path.of(file.getEncryptedPath()))) {
            model.setFileStatus(file, FileStatus.MISSING);
            model.updateFilteredFileList(PREDICATE_SHOW_ALL_FILES);
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
    }

    /**
     * Checks if the file specified is encrypted and updates the model.
     */
    public static void checkIfFileEncrypted(EncryptedFile file, Model model)
            throws CommandException, IOException {
        if (!EncryptionUtil.isFileEncrypted(file.getEncryptedPath())) {
            model.setFileStatus(file, FileStatus.CORRUPTED);
            model.updateFilteredFileList(PREDICATE_SHOW_ALL_FILES);
            throw new CommandException(MESSAGE_FILE_NOT_ENCRYPTED);
        }
    }

    /**
     * Gets the file from the displayed list with the specified index.
     */
    public static EncryptedFile getFileWithIndex(Index targetIndex, Model model) throws CommandException {
        List<EncryptedFile> lastShownList = model.getFilteredFileList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILE_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }
}

package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FILES;

import java.nio.file.Files;
import java.nio.file.Path;

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
}

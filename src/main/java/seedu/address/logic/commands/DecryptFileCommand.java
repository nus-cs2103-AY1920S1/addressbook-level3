package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FILES;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.security.GeneralSecurityException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.TargetFileExistException;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileStatus;

/**
 * Decrypts a file identified using it's displayed index from the file book.
 */
public class DecryptFileCommand extends Command {

    public static final String COMMAND_WORD = "decrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Decrypt the file identified by the index number used in the displayed file list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FILE_SUCCESS = "File decrypted: %1$s";
    public static final String MESSAGE_DELETE_FILE_FAILURE = "File decryption failed.";
    public static final String MESSAGE_FILE_IS_OPENED = "File encryption failed.\n"
            + "This file is being used by another process. Close the file and try again.";
    public static final String MESSAGE_TARGET_FILE_EXISTS = "File decryption failed. "
            + "Target file already exists.\nRename %1$s and try again.";
    public static final String MESSAGE_DECRYPT_FILE_FAILURE = "File decryption failed. "
            + "File may be corrupted. \nUse remove command to remove the file from the file list.";

    private final Index targetIndex;
    private final String password;

    public DecryptFileCommand(Index targetIndex, String password) {
        this.targetIndex = targetIndex;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EncryptedFile fileToDecrypt = FileCommandUtil.getFileWithIndex(targetIndex, model);;
        FileCommandUtil.checkIfFileExists(fileToDecrypt, model);

        try {
            FileCommandUtil.checkIfFileEncrypted(fileToDecrypt, model);
            EncryptionUtil.decryptFile(fileToDecrypt.getEncryptedPath(), fileToDecrypt.getFullPath(), password);
        } catch (FileSystemException e) {
            throw new CommandException(MESSAGE_FILE_IS_OPENED);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_DELETE_FILE_FAILURE);
        } catch (GeneralSecurityException e) {
            model.setFileStatus(fileToDecrypt, FileStatus.CORRUPTED);
            model.updateFilteredFileList(PREDICATE_SHOW_ALL_FILES);
            throw new CommandException(MESSAGE_DECRYPT_FILE_FAILURE);
        } catch (TargetFileExistException e) {
            throw new CommandException(String.format(MESSAGE_TARGET_FILE_EXISTS, e.getMessage()));
        }
        model.deleteFile(fileToDecrypt);
        return new CommandResult(String.format(MESSAGE_DELETE_FILE_SUCCESS, fileToDecrypt));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DecryptFileCommand // instanceof handles nulls
                && targetIndex.equals(((DecryptFileCommand) other).targetIndex)); // state check
    }
}


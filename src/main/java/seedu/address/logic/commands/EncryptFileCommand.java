package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Date;

import seedu.address.commons.exceptions.TargetFileExistException;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedAt;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.ModifiedAt;

/**
 * Encrypt a file identified using it's file path.
 */
public class EncryptFileCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Encrypts a file in user's file system using the specified file path.\n"
            + "Parameters: FILEPATH [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " /Users/YOUR_USERNAME/Desktop/Test.txt t/personal";

    public static final String MESSAGE_SUCCESS = "File encrypted: %1$s";
    public static final String MESSAGE_FAILURE = "File encryption failed.";
    public static final String MESSAGE_FILE_IS_OPENED = "File encryption failed.\n"
            + "This file is being used by another process. Close the file and try again.";
    public static final String MESSAGE_IS_DIRECTORY = "File encryption failed.\n"
            + "SecureIT currently does not support encrypting directories.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist.";
    public static final String MESSAGE_DUPLICATE_FILE = "This file is already in the list.";
    public static final String MESSAGE_ENCRYPTED_FILE = "This file is already encrypted.\n"
            + "Use add command to add encrypted files to the list.";
    public static final String MESSAGE_TARGET_FILE_EXISTS = "File encryption failed. "
            + "Target file already exists.\nRename %1$s and try again.";

    private final EncryptedFile toAdd;
    private final String password;

    /**
     * Creates an EncryptFileCommand to encrypt the specified {@code File}
     */
    public EncryptFileCommand(EncryptedFile file, String password) {
        requireAllNonNull(file, password);
        toAdd = file;
        this.password = password;
    }

    private static Date getModifyDateFromFile(EncryptedFile file) throws IOException {
        return new Date(Files.getLastModifiedTime(Path.of(file.getFullPath())).toMillis());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFile(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
        if (!Files.exists(Path.of(toAdd.getFullPath()))) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
        if (Files.isDirectory(Path.of(toAdd.getFullPath()))) {
            throw new CommandException(MESSAGE_IS_DIRECTORY);
        }
        try {
            if (EncryptionUtil.isFileEncrypted(toAdd.getFullPath())) {
                throw new CommandException(MESSAGE_ENCRYPTED_FILE);
            }
            toAdd.setModifiedAt(new ModifiedAt(getModifyDateFromFile(toAdd)));
            EncryptionUtil.encryptFile(toAdd.getFullPath(), toAdd.getEncryptedPath(), password);
            toAdd.setEncryptedAt(new EncryptedAt(new Date()));
        } catch (FileSystemException e) {
            throw new CommandException(MESSAGE_FILE_IS_OPENED);
        } catch (IOException | GeneralSecurityException e) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (TargetFileExistException e) {
            throw new CommandException(String.format(MESSAGE_TARGET_FILE_EXISTS, e.getMessage()));
        }
        model.addFile(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((EncryptFileCommand) other).toAdd));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedAt;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.ModifiedAt;

/**
 * Add an encrypted file identified using it's file path.
 */
public class AddFileCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an encrypted file in user's file system using the specified file path.\n"
            + "Parameters: FILEPATH [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " /Users/YOUR_USERNAME/Desktop/[LOCKED] Test.txt t/personal";

    public static final String MESSAGE_SUCCESS = "File added: %1$s";
    public static final String MESSAGE_FAILURE = "Cannot add file.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist.";
    public static final String MESSAGE_DUPLICATE_FILE = "This file is already added.";
    public static final String MESSAGE_FILE_NOT_ENCRYPTED = "File is not yet encrypted.\n"
            + "Use encrypt command to encrypt files instead.";

    private final EncryptedFile toAdd;

    /**
     * Creates an EncryptFileCommand to encrypt the specified {@code File}
     */
    public AddFileCommand(EncryptedFile file) {
        requireAllNonNull(file);
        toAdd = file;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFile(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
        String realFilePath = toAdd.getEncryptedPath();
        if (!Files.exists(Path.of(realFilePath))) {
            realFilePath = toAdd.getFullPath();
            if (!Files.exists(Path.of(realFilePath))) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
        }

        try {
            if (!EncryptionUtil.isFileEncrypted(realFilePath)) {
                throw new CommandException(MESSAGE_FILE_NOT_ENCRYPTED);
            }
            toAdd.setModifiedAt(new ModifiedAt(new Date(0)));
            toAdd.setEncryptedAt(new EncryptedAt(new Date(0)));
            new File(realFilePath).renameTo(new File(toAdd.getEncryptedPath()));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.addFile(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddFileCommand) other).toAdd));
    }
}

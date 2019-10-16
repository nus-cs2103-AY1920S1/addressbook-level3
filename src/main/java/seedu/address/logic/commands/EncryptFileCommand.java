package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;

/**
 * Encrypt a file identified using it's file path.
 */
public class EncryptFileCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Encrypts the file in user's file system specified the file path.\n"
            + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " ~/Desktop/sample.txt";

    public static final String MESSAGE_SUCCESS = "File encrypted: %1$s";
    public static final String MESSAGE_DUPLICATE_FILE = "This file is already encrypted.";

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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFile(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
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

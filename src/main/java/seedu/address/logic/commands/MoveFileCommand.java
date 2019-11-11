package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FilePath;
import seedu.address.model.file.FileStatus;

/**
 * Moves a file identified using it's displayed index from the file book.
 */
public class MoveFileCommand extends Command {

    public static final String COMMAND_WORD = "move";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Move the file identified by the index number from the displayed file list.\n"
            + "Parameters: INDEX NEW_LOCATION\n"
            + "Example: " + COMMAND_WORD + " 1 /Users/YOUR_USERNAME/Desktop";

    public static final String MESSAGE_RENAME_FILE_SUCCESS = "File moved: %1$s";
    public static final String MESSAGE_RENAME_FILE_FAILURE = "Cannot move file.";
    public static final String MESSAGE_TARGET_FILE_EXISTS = "Cannot move file. "
            + "Target file already exists.\nRename %1$s and try again.";
    public static final String MESSAGE_DUPLICATE_FILE = "Target file is already in the list.";
    public static final String MESSAGE_IS_NOT_DIRECTORY = "Target path is not a directory.\n"
            + "Moving into shortcuts, aliases and symbolic links is not supported currently.";

    private final Index targetIndex;
    private final FilePath newFilePath;

    public MoveFileCommand(Index targetIndex, FilePath newFilePath) {
        this.targetIndex = targetIndex;
        this.newFilePath = newFilePath;
    }

    private void checkIfTargetFileExists(EncryptedFile newFile) throws CommandException {
        if (Files.exists(Path.of(newFile.getEncryptedPath()))) {
            throw new CommandException(String.format(MESSAGE_TARGET_FILE_EXISTS, newFile.getEncryptedPath()));
        }
    }

    private void checkIfTargetFileDuplicated(EncryptedFile newFile, Model model) throws CommandException {
        if (model.hasFile(newFile)) {
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
    }

    private void checkIfParamIsDirectory(String param) throws CommandException {
        if (!Files.isDirectory(Path.of(param))) {
            throw new CommandException(MESSAGE_IS_NOT_DIRECTORY);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EncryptedFile fileToMove = FileCommandUtil.getFileWithIndex(targetIndex, model);
        FileCommandUtil.checkIfFileExists(fileToMove, model);

        checkIfParamIsDirectory(newFilePath.value);
        EncryptedFile newFile = new EncryptedFile(
                fileToMove.getFileName(),
                newFilePath,
                fileToMove.getFileStatus() == FileStatus.CORRUPTED ? FileStatus.CORRUPTED : FileStatus.ACTIVE,
                fileToMove.getTags(),
                fileToMove.getEncryptedAt(),
                fileToMove.getModifiedAt()
        );
        checkIfTargetFileExists(newFile);
        checkIfTargetFileDuplicated(newFile, model);
        try {
            Files.move(Path.of(fileToMove.getEncryptedPath()), Path.of(newFile.getEncryptedPath()));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_RENAME_FILE_FAILURE);
        }
        model.setFile(fileToMove, newFile);

        return new CommandResult(String.format(MESSAGE_RENAME_FILE_SUCCESS, fileToMove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoveFileCommand // instanceof handles nulls
                && targetIndex.equals(((MoveFileCommand) other).targetIndex)); // state check
    }
}


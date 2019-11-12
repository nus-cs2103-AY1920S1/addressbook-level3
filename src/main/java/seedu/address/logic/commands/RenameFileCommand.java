package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FileStatus;

/**
 * Renames a file identified using it's displayed index from the file book.
 */
public class RenameFileCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rename the file identified by the index number from the displayed file list.\n"
            + "Parameters: INDEX NEW_FILENAME\n"
            + "Example: " + COMMAND_WORD + " 1 Test";

    public static final String MESSAGE_RENAME_FILE_SUCCESS = "File renamed: %1$s";
    public static final String MESSAGE_RENAME_FILE_FAILURE = "Cannot rename file.\n"
            + "Please make sure that the target file name is acceptable by your operating system.";
    public static final String MESSAGE_TARGET_FILE_EXISTS = "Cannot rename file. "
            + "Target file already exists.\nRename %1$s and try again.";
    public static final String MESSAGE_DUPLICATE_FILE = "Target file is already in the list.";

    private final Index targetIndex;
    private final FileName newFileName;

    public RenameFileCommand(Index targetIndex, FileName newFileName) {
        this.targetIndex = targetIndex;
        this.newFileName = newFileName;
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EncryptedFile fileToRename = FileCommandUtil.getFileWithIndex(targetIndex, model);;
        FileCommandUtil.checkIfFileExists(fileToRename, model);

        EncryptedFile newFile = new EncryptedFile(
                FileName.constructWithExtension(newFileName.getFileNameWithoutExtention(),
                        fileToRename.getFileName().getExtension()),
                fileToRename.getFilePath(),
                fileToRename.getFileStatus() == FileStatus.CORRUPTED ? FileStatus.CORRUPTED : FileStatus.ACTIVE,
                fileToRename.getTags(),
                fileToRename.getEncryptedAt(),
                fileToRename.getModifiedAt()
        );
        checkIfTargetFileExists(newFile);
        checkIfTargetFileDuplicated(newFile, model);
        boolean success = new File(fileToRename.getEncryptedPath()).renameTo(
                new File(newFile.getEncryptedPath()));
        if (!success) {
            throw new CommandException(MESSAGE_RENAME_FILE_FAILURE);
        }
        model.setFile(fileToRename, newFile);

        return new CommandResult(String.format(MESSAGE_RENAME_FILE_SUCCESS, newFile));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RenameFileCommand // instanceof handles nulls
                && targetIndex.equals(((RenameFileCommand) other).targetIndex)); // state check
    }
}


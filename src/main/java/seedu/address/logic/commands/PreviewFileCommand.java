package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.commons.util.PreviewUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.ViewableFile;
import seedu.address.model.file.ViewableFileType;

/**
 * Previews a file identified using it's displayed index from the file book.
 */
public class PreviewFileCommand extends Command {

    public static final String COMMAND_WORD = "preview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Preview the file identified by the index number from the displayed file list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PREVIEW_FILE_SUCCESS = "File opened in the preview: %1$s";
    public static final String MESSAGE_PREVIEW_FILE_FAILURE = "File preview failed. "
            + "File may be corrupted, or the file format does not match its extension."
            + "\nYou may use decrypt command to try to decrypt the file, or use remove command to remove the file.";
    public static final String MESSAGE_PREVIEW_FILE_UNSUPPORTED = "File type is unsupported. "
            + "\nSecureIT currently only supports preview of txt, jpg, png, docx, and pdf files.";

    private final Index targetIndex;
    private final String password;

    public PreviewFileCommand(Index targetIndex, String password) {
        this.targetIndex = targetIndex;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EncryptedFile fileToPreview = FileCommandUtil.getFileWithIndex(targetIndex, model);
        FileCommandUtil.checkIfFileExists(fileToPreview, model);

        try {
            FileCommandUtil.checkIfFileEncrypted(fileToPreview, model);
            byte[] fileBytes = EncryptionUtil.decryptFileToBytes(fileToPreview.getEncryptedPath(), password);
            ViewableFile viewableFile;
            switch (fileToPreview.getFileExtension().toLowerCase()) {
            case "txt":
                viewableFile = new ViewableFile<>(
                        fileToPreview,
                        ViewableFileType.TEXT,
                        PreviewUtil.convertToString(fileBytes)
                );
                break;
            case "jpg":
            case "jpeg":
            case "png":
                BufferedImage image = PreviewUtil.convertToImage(fileBytes);
                if (image == null) {
                    throw new IOException("");
                }
                viewableFile = new ViewableFile<>(
                        fileToPreview,
                        ViewableFileType.IMAGE,
                        PreviewUtil.convertToImage(fileBytes)
                );
                break;
            case "pdf":
                viewableFile = new ViewableFile<>(
                        fileToPreview,
                        ViewableFileType.PDF,
                        PreviewUtil.convertToPdf(fileBytes)
                );
                break;
            case "docx":
                viewableFile = new ViewableFile<>(
                        fileToPreview,
                        ViewableFileType.WORD,
                        PreviewUtil.convertToWord(fileBytes)
                );
                break;
            default:
                throw new CommandException(MESSAGE_PREVIEW_FILE_UNSUPPORTED);
            }
            return CommandResult.builder(String.format(MESSAGE_PREVIEW_FILE_SUCCESS, fileToPreview))
                    .read()
                    .setObject(viewableFile)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            throw new CommandException(MESSAGE_PREVIEW_FILE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreviewFileCommand // instanceof handles nulls
                && targetIndex.equals(((PreviewFileCommand) other).targetIndex)); // state check
    }
}


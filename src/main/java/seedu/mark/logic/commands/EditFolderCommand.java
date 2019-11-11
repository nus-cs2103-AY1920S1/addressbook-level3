package seedu.mark.logic.commands;

import static seedu.mark.commons.core.Messages.MESSAGE_FOLDER_NOT_FOUND;
import static seedu.mark.commons.core.Messages.MESSAGE_READDING_ROOT_FOLDER;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NEW_FOLDER;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;

import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Folder;

import seedu.mark.storage.Storage;

/**
 * Edits a folder in Mark.
 */
public class EditFolderCommand extends Command {

    public static final String COMMAND_WORD = "folder-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a folder name. All bookmarks with the existing folder name will be moved to this folder too.\n"
            + "Parameters: FROM_FOLDER_NAME (must be an existing folder name) "
            + PREFIX_NEW_FOLDER + "[TO_FOLDER_NAME]\n"
            + "Example: " + COMMAND_WORD + " school " + PREFIX_NEW_FOLDER + "work";

    public static final String MESSAGE_SUCCESS =
            "Folder renamed successfully to: %1$s.\nYou can view it in the dashboard tab.";
    public static final String MESSAGE_DUPLICATE_FOLDER = "The folder %s already exists in Mark";
    public static final String MESSAGE_RENAMING_ROOT_FOLDER = "The " + Folder.DEFAULT_FOLDER_NAME
            + " folder cannot be edited!";

    private final Folder folder;
    private final Folder newFolder;

    /**
     * Creates an EditFolderCommand to add edit the specified {@code Folder}.
     * @param folder the folder to rename
     * @param newFolder the new folder
     */
    public EditFolderCommand(Folder folder, Folder newFolder) {
        requireAllNonNull(folder, newFolder);
        this.folder = folder;
        this.newFolder = newFolder;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        // special case of ROOT folder, provide better error message for user
        if (folder.equals(Folder.ROOT_FOLDER)) {
            throw new CommandException(MESSAGE_RENAMING_ROOT_FOLDER);
        }

        // trying to rename to ROOT folder, provide better error message for user
        if (newFolder.equals(Folder.ROOT_FOLDER)) {
            throw new CommandException(MESSAGE_READDING_ROOT_FOLDER);
        }

        if (!model.hasFolder(folder)) {
            throw new CommandException(String.format(MESSAGE_FOLDER_NOT_FOUND, folder));
        }

        if (model.hasFolder(newFolder)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_FOLDER, newFolder));
        }

        model.renameFolder(folder, newFolder);
        String resultMessage = String.format(MESSAGE_SUCCESS, newFolder);
        model.saveMark(resultMessage);

        return new CommandResult(resultMessage);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditFolderCommand)) {
            return false;
        }

        // state check
        EditFolderCommand otherEditFolderCommand = (EditFolderCommand) other;
        return folder.equals(otherEditFolderCommand.folder)
                && newFolder.equals(otherEditFolderCommand.newFolder);
    }
}

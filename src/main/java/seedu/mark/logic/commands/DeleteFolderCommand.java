package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_FOLDER_NOT_FOUND;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.storage.Storage;

/**
 * Deletes a folder in Mark.
 */
public class DeleteFolderCommand extends Command {

    public static final String COMMAND_WORD = "folder-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a folder with the specified name.\n"
            + "Parameters: NAME (must be an existing folder name. It cannot contain other folders/bookmarks.)\n"
            + "Example: " + COMMAND_WORD + " school ";

    public static final String MESSAGE_SUCCESS = "Folder %1$s deleted.\nYou can view the result in the dashboard tab.";

    public static final String MESSAGE_DELETING_ROOT_FOLDER = "You cannot delete the " + Folder.DEFAULT_FOLDER_NAME
            + " folder.";
    private static final String MESSAGE_FOLDER_STILL_CONTAINS_STUFF = "Your folder still contains other folders or "
            + "other bookmarks.\nPlease delete or move such bookmarks to another folder and delete the subfolders first.";
    private final Folder folder;

    /**
     * Creates a DeleteFolderCommand to delete the specified {@code Folder}.
     * @param folder the folder to delete
     */
    public DeleteFolderCommand(Folder folder) {
        requireNonNull(folder);
        this.folder = folder;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        // special case of duplicate folder, provide better error message for user
        if (folder.equals(Folder.ROOT_FOLDER)) {
            throw new CommandException(MESSAGE_DELETING_ROOT_FOLDER);
        }

        if (!model.hasFolder(folder)) {
            throw new CommandException(String.format(MESSAGE_FOLDER_NOT_FOUND, folder));
        }

        if (!model.canDeleteFolder(folder)) {
            throw new CommandException(MESSAGE_FOLDER_STILL_CONTAINS_STUFF);
        }

        model.deleteFolder(folder);
        String resultMessage = String.format(MESSAGE_SUCCESS, folder);
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
        if (!(other instanceof DeleteFolderCommand)) {
            return false;
        }

        // state check
        DeleteFolderCommand otherDeleteFolderCommand = (DeleteFolderCommand) other;
        return folder.equals(otherDeleteFolderCommand.folder);
    }
}

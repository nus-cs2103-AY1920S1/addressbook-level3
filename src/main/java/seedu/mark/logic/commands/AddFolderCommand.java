package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_READDING_ROOT_FOLDER;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARENT_FOLDER;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.storage.Storage;

/**
 * Creates a new folder in Mark.
 */
public class AddFolderCommand extends Command {

    public static final String COMMAND_WORD = "folder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new folder with the specified name.\n"
            + "Parameters: NAME (must not be an existing folder name) "
            + PREFIX_PARENT_FOLDER + "[PARENT_FOLDER]\n"
            + "Example: " + COMMAND_WORD + " school ";

    public static final String MESSAGE_SUCCESS = "New folder added: %1$s.\nYou can view it in the dashboard tab.";
    public static final String MESSAGE_DUPLICATE_FOLDER = "This folder already exists in Mark";
    public static final String MESSAGE_PARENT_FOLDER_NOT_FOUND = "The parent folder %s doesn't exist in Mark";

    private final Folder folder;
    private final Folder parentFolder;

    /**
     * Creates an AddFolderCommand to add the specified {@code Folder}.
     * @param folder the folder to add
     * @param parentFolder the parent folder to add it under
     */
    public AddFolderCommand(Folder folder, Folder parentFolder) {
        requireNonNull(folder);
        this.folder = folder;
        this.parentFolder = parentFolder == null ? Folder.ROOT_FOLDER : parentFolder;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        // special case of duplicate folder, provide better error message for user
        if (folder.equals(Folder.ROOT_FOLDER)) {
            throw new CommandException(MESSAGE_READDING_ROOT_FOLDER);
        }

        if (model.hasFolder(folder)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOLDER);
        }

        if (!model.hasFolder(parentFolder)) {
            throw new CommandException(
                    String.format(MESSAGE_PARENT_FOLDER_NOT_FOUND, parentFolder));
        }

        model.addFolder(folder, parentFolder);
        model.saveMark(String.format(MESSAGE_SUCCESS, folder));

        return new CommandResult(String.format(MESSAGE_SUCCESS, folder));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddFolderCommand)) {
            return false;
        }

        // state check
        AddFolderCommand otherAddFolderCommand = (AddFolderCommand) other;
        return folder.equals(otherAddFolderCommand.folder)
                && parentFolder.equals(otherAddFolderCommand.parentFolder);
    }
}

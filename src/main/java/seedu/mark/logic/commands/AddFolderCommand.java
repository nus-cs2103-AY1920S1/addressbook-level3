package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.parser.CliSyntax;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Folder;

/**
 * Creates a new folder in Mark.
 */
public class AddFolderCommand extends Command {

    public static final String COMMAND_WORD = "folder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new folder with the specified name "
            + "Parameters: NAME (must not be an existing folder name) "
            + CliSyntax.PREFIX_PARENT_FOLDER + "[PARENT_FOLDER]\n"
            + "Example: " + COMMAND_WORD + " school ";
    public static final String MESSAGE_DUPLICATE_FOLDER = "This folder already exists in Mark";
    public static final String MESSAGE_PARENT_FOLDER_NOT_FOUND = "The parent folder %s doesn't exist in Mark";
    public static final String MESSAGE_SUCCESS = "Folder %s added successfuly";

    private final Folder folder;
    private final Folder parentFolder;

    /**
     * Creates an AddFolderCommand to add the specified {@code Folder}.
     * @param folder the folder to add
     * @param parentFolder the parent folder to add it under
     */
    public AddFolderCommand(Folder folder,
            Folder parentFolder) {
        requireNonNull(folder);
        this.folder = folder;
        this.parentFolder = parentFolder == null ? Folder.ROOT_FOLDER : parentFolder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasFolder(folder)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOLDER);
        }

        if (!model.hasFolder(parentFolder)) {
            throw new CommandException(
                    String.format(MESSAGE_PARENT_FOLDER_NOT_FOUND, parentFolder));
        }

        model.addFolder(folder, parentFolder);
        model.saveMark();

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

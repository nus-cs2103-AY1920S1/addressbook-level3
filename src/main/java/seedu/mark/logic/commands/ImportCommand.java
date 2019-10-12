package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.storage.JsonMarkStorage;
import seedu.mark.storage.Storage;

/**
 * Imports Mark's {@code Bookmark} data from a specific file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports all of Mark's data from the specified file.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " savedBookmarks";

    public static final String MESSAGE_IMPORT_SUCCESS = "Bookmarks successfully imported from %1$s";
    public static final String MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES = MESSAGE_IMPORT_SUCCESS + "\n\n"
            + "The following bookmarks were not imported as they already exist: %2$s";
    public static final String MESSAGE_IMPORT_FAILURE = "There was a problem while trying to import bookmarks "
            + "from this file";
    public static final String MESSAGE_FILE_FORMAT_INCORRECT = "The format of data in the file %1$s is incorrect";
    public static final String MESSAGE_FILE_NOT_FOUND = "The file %1$s does not exist";

    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    private final Path filePath;

    /**
     * Creates a new {@code ImportCommand} instance with the given file path.
     *
     * @param filePath of the file to be imported from.
     */
    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Converts a given list of {@code Bookmark}s to a multi-line {@code String},
     * with each line having an indent of four spaces.
     *
     * @param bookmarks List of bookmarks.
     * @return String representation of the bookmark list with indentation.
     */
    public static String toIndentedString(List<Bookmark> bookmarks) {
        String newlineAndIndent = "\n    ";
        return bookmarks.stream().map(Bookmark::toString)
                .map(bookmark -> newlineAndIndent.concat(bookmark))
                .reduce("", String::concat);
    }

    /**
     * Executes the import command on the given {@code model} and {@code storage}.
     *
     * @param model {@code Model} which the command should operate on.
     * @param storage {@code Storage} which the command should operate on.
     * @return A CommandResult indicating a successful import, and (if applicable)
     *         shows bookmarks that were skipped during import.
     * @throws CommandException if an error occurs while reading bookmarks from Storage
     */
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        ReadOnlyMark newMark = readMarkFromStorageFile(storage, filePath);

        FolderStructure foldersToImport = newMark.getFolderStructure();
        addFoldersToModel(model, foldersToImport);

        ObservableList<Bookmark> bookmarksToImport = newMark.getBookmarkList();
        List<Bookmark> skippedBookmarks = new ArrayList<>(); // empty list to operate on
        addBookmarksToModel(model, bookmarksToImport, skippedBookmarks);

        if (bookmarksToImport.size() != skippedBookmarks.size()) {
            model.saveMark();
        }

        // TODO: refactor log and command result messages to include folder import
        if (!skippedBookmarks.isEmpty()) {
            logger.info("Bookmarks imported from " + filePath + ": Some duplicates skipped");
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES, filePath,
                    toIndentedString(skippedBookmarks)));
        }

        logger.info("Bookmarks imported from " + filePath);
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath));
    }

    /**
     * Attempts to read Mark from a given file in storage.
     */
    private ReadOnlyMark readMarkFromStorageFile(Storage storage, Path filePath) throws CommandException {
        requireAllNonNull(storage, filePath);

        Optional<ReadOnlyMark> newMark;
        try {
            newMark = storage.readMark(filePath);
        } catch (IOException ioe) {
            logger.info("Bookmarks not imported: Problem while reading from the file " + filePath);
            throw new CommandException(MESSAGE_IMPORT_FAILURE);
        } catch (DataConversionException dce) {
            logger.info("Bookmarks not imported: Data file " + filePath + " has wrong format");
            throw new CommandException(String.format(MESSAGE_FILE_FORMAT_INCORRECT, filePath));
        }

        if (newMark.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath));
        }

        return newMark.get();
    }

    /**
     * Adds bookmarks from the given list of bookmarks to the model and adds
     * duplicate bookmarks to the list of skipped bookmarks.
     */
    private void addBookmarksToModel(Model model, ObservableList<Bookmark> bookmarksToAdd,
                                     List<Bookmark> skippedBookmarks) {
        for (Bookmark bookmark : bookmarksToAdd) {
            if (model.hasBookmark(bookmark)) {
                logger.fine("Duplicate bookmark was not imported: " + bookmark);
                skippedBookmarks.add(bookmark);
                continue;
            }
            logger.fine("Bookmark imported: " + bookmark);
            model.addBookmark(bookmark);
        }
    }


    /**
     * Adds the given folder structure to the model. Implementation to be decided.
     */
    private void addFoldersToModel(Model model, FolderStructure foldersToImport) {
        // TODO: decide what to do here

        // option 1:
        // get ROOT
        // add subfolders of imported folder structure to ROOT
        // check for duplicate folders and ignore them
            // if folder is found, then ignore
            // for each Bookmark in list, if name = duplicate-folder, change folder to ROOT

        // option 2:
        // get ROOT
        // create a new subfolder for imported bookmarks (de-conflict names if necessary)
        // import each folder into import-folder
        // check for duplicate folders and rename if necessary (e.g. folder-1)
            // for each Bookmark in list, if name = renamed-folder, change name to new-name
    }

    /**
     * Checks whether an object is equal to this {@code ImportCommand}.
     *
     * @param other Object to compare.
     * @return true if the object is an ImportCommand with the same {@code filePath}
     * as the current {@code ImportCommand}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}

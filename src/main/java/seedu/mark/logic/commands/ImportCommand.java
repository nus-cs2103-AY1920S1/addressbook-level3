package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
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
    public static final String MESSAGE_NO_BOOKMARKS_TO_IMPORT = "There are no new bookmarks to import. "
            + "The following bookmarks already exist: %1$s";
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
     * Attempts to read Mark from a given file in storage.
     */
    private static ReadOnlyMark readMarkFromStorage(Storage storage, Path filePath) throws CommandException {
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

        ReadOnlyMark newMark = readMarkFromStorage(storage, filePath);

        MarkImporter importer = new MarkImporter(model, newMark);
        importer.importFolders();

        if (!importer.hasBookmarksToImport()) {
            return new CommandResult(String.format(MESSAGE_NO_BOOKMARKS_TO_IMPORT,
                    importer.getExistingBookmarksAsString()));
        }
        importer.importBookmarks();

        // TODO: rewrite messages to indicate whether folders were imported
        String message = importer.hasExistingBookmarks()
                ? String.format(MESSAGE_IMPORT_SUCCESS_WITH_DUPLICATES, filePath,
                    importer.getExistingBookmarksAsString())
                : String.format(MESSAGE_IMPORT_SUCCESS, filePath);
        return new CommandResult(message);
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

    /**
     * Stores data from a {@code Mark} to import them into a {@code Model}.
     */
    public static class MarkImporter {
        private Model model;
        private FolderStructure foldersToImport;
        private List<Bookmark> existingBookmarks = new ArrayList<>();
        private List<Bookmark> bookmarksToImport = new ArrayList<>();

        MarkImporter(Model model, ReadOnlyMark markToImport) {
            this.model = model;
            this.foldersToImport = markToImport.getFolderStructure();
            for (Bookmark bookmark : markToImport.getBookmarkList()) {
                if (model.hasBookmark(bookmark)) {
                    this.existingBookmarks.add(bookmark);
                } else {
                    this.bookmarksToImport.add(bookmark);
                }
            }
        }

        private Model getModel() {
            return this.model;
        }

        public FolderStructure getFolders() {
            return this.foldersToImport;
        }

        /**
         * Checks whether the {@code MarkImporter} contains bookmarks that
         * already exist in the {@code model}.
         *
         * @return true if some bookmarks already exist in the model, and
         *         false otherwise
         */
        public boolean hasExistingBookmarks() {
            return !existingBookmarks.isEmpty();
        }

        /**
         * Returns a list of bookmarks that already exist in the {@code model}.
         */
        public List<Bookmark> getExistingBookmarks() {
            return existingBookmarks;
        }

        /**
         * Returns the list of existing bookmarks as a multi-line {@code String}
         * with 4 spaces of indentation per line.
         *
         * @return String representation of existing bookmarks with indentation.
         */
        public String getExistingBookmarksAsString() {
            String newlineAndIndent = "\n    ";
            return existingBookmarks.stream().map(Bookmark::toString)
                    .map(newlineAndIndent::concat)
                    .reduce("", String::concat);
        }

        /**
         * Checks whether the {code MarkImporter} has new bookmarks to
         * import.
         *
         * @return true if some bookmarks do not already exist in the model, and
         *         false otherwise
         */
        public boolean hasBookmarksToImport() {
            return !bookmarksToImport.isEmpty();
        }

        /**
         * Returns the bookmarks in the given list that do not already exist in
         * the {@code model}.
         */
        public List<Bookmark> getBookmarksToImport() {
            return bookmarksToImport;
        }

        /**
         * Imports new bookmarks to the {@code model} and saves the state of
         * Mark.
         */
        public void importBookmarks() {
            model.addBookmarks(bookmarksToImport);
            model.saveMark();
        }

        public void importFolders() {
            model.addFolders(foldersToImport);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof MarkImporter)) {
                return false;
            }

            // state check
            MarkImporter markImporter = (MarkImporter) other;

            return getModel().equals(markImporter.getModel())
                    && getFolders().equals(markImporter.getFolders())
                    && getExistingBookmarks().equals(markImporter.getExistingBookmarks())
                    && getBookmarksToImport().equals(markImporter.getBookmarksToImport());
        }
    }
}

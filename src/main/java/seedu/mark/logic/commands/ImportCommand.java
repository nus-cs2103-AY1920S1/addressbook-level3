package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyMark;
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
    public static final String MESSAGE_IMPORT_FAILURE = "There was a problem while trying to import bookmarks "
            + "from this file";
    public static final String MESSAGE_FILE_FORMAT_INCORRECT = "The format of data in the file %1$s is incorrect";
    public static final String MESSAGE_FILE_NOT_FOUND = "The file %1$s does not exist";

    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    private final Path filePath;

    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);

        Optional<ReadOnlyMark> newMark;

        try {
            newMark = storage.readMark(filePath);
        } catch (IOException ioe) {
            logger.info("Problem while reading from the file. Bookmarks not imported");
            throw new CommandException(MESSAGE_IMPORT_FAILURE);
        } catch (DataConversionException dce) {
            logger.info("Data file not in the correct format. Bookmarks not imported");
            throw new CommandException(String.format(MESSAGE_FILE_FORMAT_INCORRECT, filePath));
        }

        assert newMark != null;
        if (newMark.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath));
        }

        model.setMark(newMark.get());

        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}

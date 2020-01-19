package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.JsonMarkStorage;
import seedu.mark.storage.Storage;

/**
 * Exports Mark's {@code Bookmark} data into a specific file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all of Mark's data to the specified file.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " savedBookmarks";

    public static final String MESSAGE_EXPORT_SUCCESS = "Bookmarks successfully exported to %1$s";
    public static final String MESSAGE_EXPORT_FAILURE = "Bookmarks could not be exported";

    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    private final Path filePath;

    public ExportCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        try {
            storage.saveMark(model.getMark(), filePath);
        } catch (IOException ioe) {
            logger.info("Bookmarks not exported: Error while writing to file " + filePath);
            throw new CommandException(MESSAGE_EXPORT_FAILURE);
        }

        logger.info("Bookmarks exported to file " + filePath);
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && filePath.equals(((ExportCommand) other).filePath)); // state check
    }
}

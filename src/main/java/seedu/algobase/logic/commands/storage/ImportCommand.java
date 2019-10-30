package seedu.algobase.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.algobase.commons.exceptions.DataConversionException;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.commons.util.FileUtil.Format;
import seedu.algobase.commons.util.JsonUtil;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.storage.JsonSerializableAlgoBase;

/**
 * Imports AlgoBase from specified location.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports AlgoBase from a specified location.\n"
            + "Parameters:\n"
            + PREFIX_FORMAT + "FORMAT "
            + PREFIX_PATH + "PATH\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_FORMAT + "JSON "
            + PREFIX_PATH + "./";

    public static final String MESSAGE_EXPORT_SUCCESS = "AlgoBase data successfully imported from [%1$s].";

    private final Format format;
    private final String path;

    public ImportCommand(Format format, String path) {
        this.format = format;
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Path filePath = Paths.get(path);

        try {
            Optional<JsonSerializableAlgoBase> jsonAlgoBase = JsonUtil.readJsonFile(
                    filePath, JsonSerializableAlgoBase.class);
            model.setAlgoBase(jsonAlgoBase.get().toModelType());
        } catch (DataConversionException | IllegalValueException | NoSuchElementException e) {
            return new CommandResult("Invalid data.");
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && format.equals(((ImportCommand) other).format)
                && path.equals(((ImportCommand) other).path)); // state check
    }

}

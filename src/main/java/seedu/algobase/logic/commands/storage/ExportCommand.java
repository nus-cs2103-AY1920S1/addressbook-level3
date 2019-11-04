package seedu.algobase.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.algobase.commons.util.FileUtil;
import seedu.algobase.commons.util.FileUtil.Format;
import seedu.algobase.commons.util.JsonUtil;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.storage.JsonSerializableAlgoBase;

/**
 * Exports AlgoBase to specified location.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports AlgoBase to the specified location.\n"
            + "Parameters:\n"
            + PREFIX_FORMAT + "FORMAT "
            + PREFIX_PATH + "PATH\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_FORMAT + "JSON "
            + PREFIX_PATH + "./";

    public static final String MESSAGE_EXPORT_SUCCESS = "AlgoBase data exported to [%1$s].";

    private final Format format;
    private final String path;

    public ExportCommand(Format format, String path) {
        this.format = format;
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Path filePath = Paths.get(path, "algobase.json");
        try {
            FileUtil.createIfMissing(filePath);
            JsonUtil.saveJsonFile(new JsonSerializableAlgoBase(model.getAlgoBase()), filePath);
        } catch (IOException e) {
            return new CommandResult("Filepath invalid.");
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && format.equals(((ExportCommand) other).format)
                && path.equals(((ExportCommand) other).path)); // state check
    }

}

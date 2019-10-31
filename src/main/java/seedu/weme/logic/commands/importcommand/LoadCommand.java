package seedu.weme.logic.commands.importcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;

/**
 * Loads memes from a directory to the import context.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": loads memes to the import staging area from a given directory. ";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: "
            + PREFIX_FILEPATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:/Users/username/Downloads/ ";
    public static final String MESSAGE_SUCCESS = "Memes loaded successfully to the import staging area.";
    public static final String MESSAGE_LOAD_FAILURE = "Invalid directory path given.";
    private final DirectoryPath importDirectoryPath;

    /**
     * Creates a LoadCommand to import the memes from the specified {@code Path}.
     */
    public LoadCommand(DirectoryPath path) {
        requireNonNull(path);
        importDirectoryPath = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Path> pathList = FileUtil.loadImagePath(importDirectoryPath);
            model.loadMemes(pathList);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_LOAD_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && importDirectoryPath.equals(((LoadCommand) other).importDirectoryPath));
    }

}

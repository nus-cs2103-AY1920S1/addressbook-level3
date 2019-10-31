package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveFileCommand;
import seedu.address.logic.commands.RenameFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.file.FilePath;

/**
 * Parses input arguments and creates a new MoveFileCommand object
 */
public class MoveFileCommandParser implements Parser<MoveFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MoveFileCommand
     * and returns a MoveFileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveFileCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            Index index = ParserUtil.parseIndex(trimmedArgs.split(" ")[0]);
            FilePath newPath = new FilePath(trimmedArgs.substring(trimmedArgs.indexOf(' ') + 1));
            return new MoveFileCommand(index, newPath);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameFileCommand.MESSAGE_USAGE), pe);
        }
    }

}

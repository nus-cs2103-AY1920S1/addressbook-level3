package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RenameFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.file.FileName;

/**
 * Parses input arguments and creates a new RenameFileCommand object
 */
public class RenameFileCommandParser implements Parser<RenameFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameFileCommand
     * and returns a RenameFileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameFileCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            Index index = ParserUtil.parseIndex(trimmedArgs.split(" ")[0]);
            if (trimmedArgs.indexOf(' ') == -1) {
                throw new ParseException("");
            }
            FileName newName = new FileName(trimmedArgs.substring(trimmedArgs.indexOf(' ') + 1));
            return new RenameFileCommand(index, newName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameFileCommand.MESSAGE_USAGE), pe);
        }
    }

}

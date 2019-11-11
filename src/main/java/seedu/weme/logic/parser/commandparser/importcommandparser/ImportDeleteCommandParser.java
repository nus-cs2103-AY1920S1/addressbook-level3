package seedu.weme.logic.parser.commandparser.importcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.importcommand.ImportDeleteCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new ImportDeleteCommand object
 */
public class ImportDeleteCommandParser implements Parser<ImportDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportDeleteCommand
     * and returns a ImportDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ImportDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

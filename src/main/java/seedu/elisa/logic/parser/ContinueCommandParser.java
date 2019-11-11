package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INCORRECT_SYMBOL_USAGE;
import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.elisa.commons.core.index.Index;
import seedu.elisa.logic.commands.ContinueCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ContinueCommand object
 */
public class ContinueCommandParser implements Parser<ContinueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ContinueCommand
     * and returns a ContinueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ContinueCommand parse(String args, String flags) throws ParseException {
        // flags should be empty in this case, focus on args only
        // if flags is not empty, it means symbol "-" is present
        if (!flags.equals(" ")) {
            throw new ParseException(MESSAGE_INCORRECT_SYMBOL_USAGE);
        }

        // if index is not provided
        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, " I need an index! \n"
                    + ContinueCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(args);
        return new ContinueCommand(index);
    }

}

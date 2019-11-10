package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SIGNATURE_FORMAT;

import seedu.address.logic.commands.GenReportsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author bernicechio

/**
 * Parses input signature and creates a new GenReportsCommand object.
 */
public class GenReportsCommandParser implements Parser<GenReportsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportsCommand
     * and returns a GenReportsCommand object for execution.
     */
    public GenReportsCommand parse(String args) throws ParseException {
        if (!args.matches("^[ A-Za-z]+$") || args.length() > 40) {
            throw new ParseException(MESSAGE_INVALID_SIGNATURE_FORMAT);
        }
        return new GenReportsCommand(args);
    }

}

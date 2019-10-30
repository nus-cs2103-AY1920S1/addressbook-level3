package seedu.address.logic.parser.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ManualAllocateCommand object
 */
public class ManualAllocateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ManualAllocateCommand
     * and returns an ManualAllocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ManualAllocateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_NUMBER, PREFIX_EMPLOYEE_ID);

        Index index;
        Index eventIndex;

        if (argMultimap.getValue(PREFIX_EMPLOYEE_ID).isPresent()) { //internal call by UI, guaranteed no exceptions
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            String employeeId = argMultimap.getValue(PREFIX_EMPLOYEE_ID).get();
            return new ManualAllocateCommand(eventIndex, employeeId);
        }
        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());

            if (argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).isPresent()) {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).get());
            } else {
                throw new ParseException("Invalid Employee Index!");
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ManualAllocateCommand.MESSAGE_USAGE), pe);
        }

        return new ManualAllocateCommand(eventIndex, index);
    }
}

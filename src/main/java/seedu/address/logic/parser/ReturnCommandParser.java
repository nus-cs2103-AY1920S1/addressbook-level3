package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReturnCommand object.
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReturnCommand
     * and returns a ReturnCommand object for execution.
     *
     * @param userInput User input.
     * @return ReturnCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    @Override
    public ReturnCommand parse(String userInput) throws ParseException {
        if (ParserUtil.onlyAllFlagPresent(userInput, ReturnCommand.COMMAND_WORD)) {
            return new ReturnCommand(); // -all flag present, return all valid books
        }

        try { // parse by index instead
            Index index = ParserUtil.parseIndex(userInput);
            return new ReturnCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE), pe);
        }
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a RenewCommand object.
 */
public class RenewCommandParser implements Parser<RenewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenewCommand
     * and returns a RenewCommand object for execution.
     *
     * @param userInput User input.
     * @return RenewCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    @Override
    public RenewCommand parse(String userInput) throws ParseException {
        if (ParserUtil.onlyAllFlagPresent(userInput, RenewCommand.COMMAND_WORD)) {
            return new RenewCommand(); // -all flag present, renew all valid books
        }

        try { // parse by index instead
            Index index = ParserUtil.parseIndex(userInput);
            return new RenewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE), pe);
        }
    }
}

package io.xpire.logic.parser;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;

import io.xpire.logic.commands.ShiftToReplenishCommand;
import io.xpire.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShiftToReplenishCommand object.
 */
public class ShiftToReplenishCommandParser implements Parser<ShiftToReplenishCommand> {

    @Override
    public ShiftToReplenishCommand parse(String userInput) throws ParseException {
        String[] splitArgs = userInput.split("\\|", 1);
        if (splitArgs.length > 1) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ShiftToReplenishCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(splitArgs[0]);
        return new ShiftToReplenishCommand(index);
    }
}

package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.logic.commands.ShiftToMainCommand.MESSAGE_USAGE;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.ShiftToMainCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Quantity;

/**
 * Parses input arguments and creates a new ShiftToMainCommand object.
 */
public class ShiftToMainCommandParser implements Parser<ShiftToMainCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShiftToMainCommand
     * and returns an ShiftToMainCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ShiftToMainCommand parse(String args) throws ParseException {
        String[] arguments = args.split("\\|", 3);
        if (!areArgumentsPresent(arguments)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(arguments[0]);
        ExpiryDate expiryDate = ParserUtil.parseExpiryDate(arguments[1]);
        Quantity quantity;
        if (hasQuantity(arguments)) {
            quantity = ParserUtil.parseQuantity(arguments[2]);
        } else {
            quantity = new Quantity("1");
        }
        return new ShiftToMainCommand(index, expiryDate, quantity);
    }

    private static boolean areArgumentsPresent(String...arguments) {
        return arguments.length >= 2;
    }

    private static boolean hasQuantity(String...arguments) {
        return arguments.length >= 3 && !arguments[2].equals("");
    }

}

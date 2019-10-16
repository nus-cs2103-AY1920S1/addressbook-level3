package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.format.DateTimeParseException;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String[] arguments = args.split("\\|", 3);
        if (!areArgumentsPresent(arguments)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        Name name;
        ExpiryDate expiryDate;
        Item item;
        try {
            name = ParserUtil.parseName(arguments[0]);
            expiryDate = ParserUtil.parseExpiryDate(arguments[1]);
        } catch (DateTimeParseException | ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (hasQuantity(arguments)) {
            Quantity quantity = ParserUtil.parseQuantity(arguments[2]);
            item = new Item(name, expiryDate, quantity);
        } else {
            item = new Item(name, expiryDate);
        }
        return new AddCommand(item);
    }

    private static boolean areArgumentsPresent(String...arguments) {
        return arguments.length >= 2;
    }

    private static boolean hasQuantity(String...arguments) {
        return arguments.length >= 3 && !arguments[2].equals("");
    }

}

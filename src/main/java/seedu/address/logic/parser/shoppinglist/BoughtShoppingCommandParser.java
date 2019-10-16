package seedu.address.logic.parser.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.shoppinglist.BoughtShoppingCommand;
import seedu.address.logic.commands.shoppinglist.EditShoppingCommand;
import seedu.address.logic.commands.shoppinglist.BoughtShoppingCommand.BoughtShoppingItemDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditShoppingCommand object
 */
public class BoughtShoppingCommandParser implements Parser<BoughtShoppingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BoughtShoppingCommand
     * and returns an BoughtShoppingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BoughtShoppingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXPIRY_DATE, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditShoppingCommand.MESSAGE_USAGE), pe);
        }

        BoughtShoppingItemDescriptor boughtShoppingItemDescriptor = new BoughtShoppingItemDescriptor();
        if (argMultimap.getValue(PREFIX_EXPIRY_DATE).isPresent()) {
            boughtShoppingItemDescriptor.setExpiryDate(
                    ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            boughtShoppingItemDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        return new BoughtShoppingCommand(index, boughtShoppingItemDescriptor);
    }
}
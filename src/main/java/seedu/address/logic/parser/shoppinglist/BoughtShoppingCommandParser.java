package seedu.address.logic.parser.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.shoppinglist.BoughtShoppingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;

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
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_EXPIRY_DATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BoughtShoppingCommand.MESSAGE_USAGE), pe);
        }

        if (!(argMultimap.getValue(PREFIX_EXPIRY_DATE).isPresent() &&
                argMultimap.getValue(PREFIX_AMOUNT).isPresent())) {
            throw new ParseException(BoughtShoppingCommand.MESSAGE_NOT_PROPER);
        }

        ExpiryDate expiryDate = ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        return new BoughtShoppingCommand(index, amount, expiryDate);
    }
}

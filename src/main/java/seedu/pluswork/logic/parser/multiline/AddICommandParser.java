package seedu.pluswork.logic.parser.multiline;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.util.stream.Stream;

import seedu.pluswork.logic.commands.multiline.AddICommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.MemberId;

/**
 * Parses the given {@code String} of arguments in the context of the AddInventoryCommand
 * and returns an AddInventoryCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddICommandParser implements Parser<AddICommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddICommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_PRICE, PREFIX_MEMBER_ID);

        //parse MemberId
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddICommand.MESSAGE_USAGE));
        }
        MemberId memId = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());

        //parse price
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddICommand.MESSAGE_USAGE));
        }
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_INVENTORY_PRICE).get());

        return new AddICommand(price, memId);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

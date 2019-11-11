package seedu.pluswork.logic.parser.inventory;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.MemberId;

/**
 * Parses the given {@code String} of arguments in the context of the AddInventoryCommand
 * and returns an AddInventoryCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddInventoryCommandParser implements Parser<AddInventoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_NAME,
                        PREFIX_INVENTORY_PRICE, PREFIX_TASK_INDEX, PREFIX_MEMBER_ID);

        //parse inventory name
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE));
        }
        InvName name = ParserUtil.parseInvName(argMultimap.getValue(PREFIX_INVENTORY_NAME).get());

        //parse taskID
        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE));
        }
        Index taskId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());

        //parse MemberId
        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE));
        }
        MemberId memId = ParserUtil.parseMemberId(argMultimap.getValue(PREFIX_MEMBER_ID).get());

        //parse price
        if (argMultimap.getValue(PREFIX_INVENTORY_PRICE).isPresent()) {
            Price price = ParserUtil.parsePrice((argMultimap.getValue(PREFIX_INVENTORY_PRICE).get()));
            return new AddInventoryCommand(taskId, name, price, memId);
        } else {
            return new AddInventoryCommand(taskId, name, memId);
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

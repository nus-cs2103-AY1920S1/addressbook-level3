package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_TASKID;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInventoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Price;

public class AddInventoryCommandParser implements Parser<AddInventoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_NAME,
                        PREFIX_INVENTORY_PRICE, PREFIX_INVENTORY_TASKID);

        //parse inventory name
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE));
        }
        InvName name = ParserUtil.parseInvName(argMultimap.getValue(PREFIX_INVENTORY_NAME).get());

        //parse taskID
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_TASKID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE));
        }
        Index taskID = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INVENTORY_TASKID).get());

        //parse price
        if (argMultimap.getValue(PREFIX_INVENTORY_PRICE).isPresent()) {
            Price price = ParserUtil.parsePrice((argMultimap.getValue(PREFIX_INVENTORY_PRICE).get()));
            //Inventory inventory = new Inventory(taskID, name, price);
            return new AddInventoryCommand(taskID, name, price);
        } else {
            //Inventory inventory = new Inventory(taskID, name);
            return new AddInventoryCommand(taskID, name);
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

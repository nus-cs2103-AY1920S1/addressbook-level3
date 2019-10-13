package seedu.address.inventory.logic;

import static seedu.address.inventory.logic.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.inventory.logic.CliSyntax.PREFIX_COST;
import static seedu.address.inventory.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.inventory.logic.CliSyntax.PREFIX_PRICE;
import static seedu.address.inventory.logic.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.inventory.commands.AddCommand;
import seedu.address.inventory.logic.exception.ParseException;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Parser for Add Commands.
 */
public class AddCommandParser {
    public static AddCommand parse(String args, int inventoryListSize) throws ParseException {
        if (args.contains("/p")) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST, PREFIX_PRICE);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST, PREFIX_PRICE) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
            }

            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();
            String priceString = argMultimap.getValue(PREFIX_PRICE).get();
            int quantity = Integer.parseInt(quantityString);
            double cost = Double.parseDouble(costString);
            double price = Double.parseDouble(priceString);
            Item item = new Item(description, category, quantity, cost, price, inventoryListSize + 1);
            AddCommand addCommand = new AddCommand(item);
            return addCommand;
        } else {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
            }

            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();
            int quantity = Integer.parseInt(quantityString);
            double cost = Double.parseDouble(costString);
            Item item = new Item(description, category, quantity, cost, inventoryListSize + 1);
            AddCommand addCommand = new AddCommand(item);
            return addCommand;
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /*private static boolean isPricePresent(ArgumentMultimap argumentMultimap, Prefix price) {
        return argumentMultimap.getValue(price);
    }*/
}

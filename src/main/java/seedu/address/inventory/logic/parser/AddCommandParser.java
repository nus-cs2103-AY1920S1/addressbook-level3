package seedu.address.inventory.logic.parser;

import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_COST;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.inventory.logic.commands.AddCommand;
import seedu.address.inventory.logic.commands.exception.NotANumberException;
import seedu.address.inventory.logic.parser.exception.ParseException;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parser for Add Commands.
 */
public class AddCommandParser {
    /**
     * Parses the input and returns an AddCommand.
     */
    public static AddCommand parse(String args, InventoryList inventoryList) throws ParseException,
            NumberFormatException, NotANumberException, NoSuchItemException {
        int index = inventoryList.size() + 1;
        if (args.contains(" p/")) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST, PREFIX_PRICE);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST, PREFIX_PRICE) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
            }

            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();
            String priceString = argMultimap.getValue(PREFIX_PRICE).get();

            if (!isValidNumeric(quantityString) || !isValidNumeric(costString) || !isValidNumeric(priceString)) {
                throw new NotANumberException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }

            int quantity = Integer.parseInt(quantityString);
            double cost = Double.parseDouble(costString);
            double price = Double.parseDouble(priceString);
            Item item = new Item(description, category, quantity, cost, price, index);

            AddCommand addCommand = null;
            if (inventoryList.containsItem(item)) {
                int itemIndex = inventoryList.getIndex(description);
                Item current = inventoryList.get(itemIndex);
                current.setQuantity(current.getQuantity() + quantity);
                current.setPrice(price);
                addCommand = new AddCommand(current, true);
            } else {
                addCommand = new AddCommand(item, false);
            }
            return addCommand;
        } else {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
            }

            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();

            if (!isValidNumeric(quantityString) || !isValidNumeric(costString)) {
                throw new NotANumberException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }

            int quantity = Integer.parseInt(quantityString);
            double cost = Double.parseDouble(costString);
            Item item = new Item(description, category, quantity, cost, index);
            AddCommand addCommand = null;
            if (inventoryList.containsItem(item)) {
                int itemIndex = inventoryList.getIndex(description);
                Item current = inventoryList.get(itemIndex);
                current.setQuantity(current.getQuantity() + quantity);
                addCommand = new AddCommand(current, true);
            } else {
                addCommand = new AddCommand(item, false);
            }
            return addCommand;
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    private static boolean isValidNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?") && (Double.parseDouble(strNum) > 0);
    }
}

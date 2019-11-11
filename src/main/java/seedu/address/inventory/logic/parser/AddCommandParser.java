package seedu.address.inventory.logic.parser;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_ON_CASHIER_MODE;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_COST;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.cashier.model.ModelManager;
import seedu.address.inventory.logic.commands.AddCommand;
import seedu.address.inventory.logic.commands.exception.NotANumberException;
import seedu.address.inventory.logic.parser.exception.InvalidNumberException;
import seedu.address.inventory.logic.parser.exception.OnCashierModeException;
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
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @throws NoSuchItemException if the user inputs an Item not currently in the database
     * @throws OnCashierModeException if the user attempts to use the AddCommand while the Cashier Mode is active
     * @throws InvalidNumberException if the user inputs an invalid number
     * @throws NotANumberException if the user inputs a non-numeric input where a numeric input is expected
     */
    public static AddCommand parse(String args, InventoryList inventoryList) throws ParseException,
            NoSuchItemException, OnCashierModeException, InvalidNumberException, NotANumberException {
        int index = inventoryList.size() + 1;

        //checks if the user input contains the price prefix.
        if (args.contains(" p/")) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST, PREFIX_PRICE);

            //checks if the user input is missing any of the prefixes.
            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST, PREFIX_PRICE) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
            }

            //retrieves the input following each prefix as separate strings.
            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();
            String priceString = argMultimap.getValue(PREFIX_PRICE).get();

            //checks the validity of the input using isValidNumeric.
            if (!isValidNumericString(quantityString) || !isValidNumericString(costString)
                    || !isValidNumericString(priceString)) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }

            //retrieves all numeric values as their respective numeric types and creates an Item.
            int quantity = Integer.parseInt(quantityString);
            if (quantity == 0) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_QUANTITY_CANNOT_BE_ZERO);
            }
            double cost = Double.parseDouble(costString);
            double price = Double.parseDouble(priceString);
            Item item = new Item(description, category, quantity, cost, price, index);

            //checks the validity of the Item's total values.
            if (!isValidNumericString(String.valueOf(item.getTotalCost()))
                    || !isValidNumericString(String.valueOf(item.getSubtotal()))) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_TOTAL_TOO_LARGE);
            }

            AddCommand addCommand = null;

            //handles adding of Items with the same description.
            if (inventoryList.containsItem(item)) {
                if (ModelManager.onCashierMode()) {
                    throw new OnCashierModeException(MESSAGE_ON_CASHIER_MODE);
                }
                //calculates the new quantity, cost/unit and price and updates the total cost and price subtotal.
                int itemIndex = inventoryList.getIndex(description);
                Item current = inventoryList.get(itemIndex);
                int qty = current.getQuantity() + quantity;
                double cst = (current.getTotalCost() + item.getTotalCost()) / qty;
                current.setCost(cst);
                current.setQuantity(qty);
                current.setPrice(price);
                current.updateSubtotal();
                current.updateTotalCost();
                addCommand = new AddCommand(current, true);
            } else {
                addCommand = new AddCommand(item, false);
            }
            return addCommand;
        } else {
            //runs in the event that the input does not include the price prefix.
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                            PREFIX_COST);

            //checks if the user input is missing any of the prefixes.
            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY,
                    PREFIX_COST) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
            }

            //retrieves the input following each prefix as separate strings.
            String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
            String category = argMultimap.getValue(PREFIX_CATEGORY).get();
            String costString = argMultimap.getValue(PREFIX_COST).get();

            //checks the validity of the input using isValidNumeric.
            if (!isValidNumericString(quantityString) || !isValidNumericString(costString)) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }

            //retrieves all numeric values as their respective numeric types and creates an Item.
            int quantity = Integer.parseInt(quantityString);
            if (quantity == 0) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_QUANTITY_CANNOT_BE_ZERO);
            }
            double cost = Double.parseDouble(costString);
            Item item = new Item(description, category, quantity, cost, index);

            //checks the validity of the Item's total values.
            if (!isValidNumericString(String.valueOf(item.getTotalCost()))) {
                throw new InvalidNumberException(InventoryMessages.MESSAGE_TOTAL_TOO_LARGE);
            }

            AddCommand addCommand = null;

            //handles adding of Items with the same description.
            if (inventoryList.containsItem(item)) {
                if (ModelManager.onCashierMode()) {
                    throw new OnCashierModeException(MESSAGE_ON_CASHIER_MODE);
                }
                //calculates the new quantity and cost/unit and updates the total cost
                int itemIndex = inventoryList.getIndex(description);
                Item current = inventoryList.get(itemIndex);
                int qty = current.getQuantity() + quantity;
                double cst = (current.getTotalCost() + item.getTotalCost()) / qty;
                current.setCost(cst);
                current.setQuantity(qty);
                current.updateTotalCost();
                addCommand = new AddCommand(current, true);
            } else {
                addCommand = new AddCommand(item, false);
            }
            return addCommand;
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the argument is a positive numeric value less than 10,000.
     * @throws NotANumberException if the argument is not a number
     */
    private static boolean isValidNumericString(String strNum) throws NotANumberException {
        boolean isValid = false;
        try {
            isValid = Double.parseDouble(strNum) >= 0 && Double.parseDouble(strNum) < 10000;
        } catch (Exception e) {
            throw new NotANumberException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
        }
        return isValid;
    }
}

package seedu.address.cashier.logic;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_A_NUMBER;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.cashier.commands.AddCommand;
import seedu.address.cashier.logic.exception.InsufficientAmountException;
import seedu.address.cashier.logic.exception.NotANumberException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @param args to be passed in
     * @param modelManager which the command operates on
     * @throws Exception if the user input does not conform the expected format
     */
    public static AddCommand parse(String args, ModelManager modelManager) throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            throw new NotANumberException(QUANTITY_NOT_A_NUMBER);
        }

        modelManager.updateRecentInventory();
        // if the item with the specified description is not present
        if (!modelManager.hasItemInInventory(description)) {
            throw new NoSuchItemException(NO_SUCH_ITEM_CASHIER);
        }
        // if the item with the specified description is not available for sale
        if (!modelManager.isSellable(description)) {
            throw new NoSuchItemException(NO_SUCH_ITEM_FOR_SALE_CASHIER);
        }

        if (!modelManager.hasSufficientQuantity(description, quantity)) {
            int quantityLeft = modelManager.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }
        if (modelManager.hasItemInInventory(description) && modelManager.hasSufficientQuantity(description, quantity)) {
            AddCommand addCommand = new AddCommand(description, quantity);
            return addCommand;
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}


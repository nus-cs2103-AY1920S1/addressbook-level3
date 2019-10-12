package seedu.address.cashier.logic;

import static seedu.address.cashier.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.cashier.commands.AddCommand;
import seedu.address.cashier.logic.exception.InsufficientAmountException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierUi;


public class AddCommandParser {

    public static AddCommand parse(String args, ModelManager modelManager) throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(CashierUi.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        int quantity = Integer.parseInt(quantityString);

        modelManager.updateRecentInventory();

        if (!modelManager.hasItemInInventory(description)) {
            throw new NoSuchItemException(CashierUi.NO_SUCH_ITEM_CASHIER);
        }
        if (!modelManager.hasSufficientQuantity(description, quantity)) {
            int quantityLeft = modelManager.getStockLeft(description);
            throw new InsufficientAmountException(CashierUi.insufficientStock(String.valueOf(quantityLeft), description));
        }
        if (modelManager.hasItemInInventory(description) && modelManager.hasSufficientQuantity(description, quantity)) {
            AddCommand addCommand = new AddCommand(description, quantity);
            return addCommand;
        }
        return null;
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}


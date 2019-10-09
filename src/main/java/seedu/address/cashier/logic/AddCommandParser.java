package seedu.address.cashier.logic;

import static seedu.address.cashier.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.cashier.commands.AddCommand;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierUi;


public class AddCommandParser {

    public static AddCommand parse(String args, ModelManager modelManager) throws ParseException, NoSuchItemException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(CashierUi.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        int quantity = Integer.parseInt(quantityString);
        boolean isValidDescription = false;


        try {
            if (modelManager.hasItemInInventory(description)) {
                AddCommand addCommand = new AddCommand(description, quantity);
                return addCommand;
            } else {
                System.out.println("invalid add command");
                throw new NoSuchItemException(CashierUi.NO_SUCH_ITEM_CASHIER);
            }
        } catch (Exception e) {
            throw new ParseException(CashierUi.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}


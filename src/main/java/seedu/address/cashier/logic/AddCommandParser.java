package seedu.address.cashier.logic;

import static seedu.address.cashier.logic.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.cashier.commands.AddCommand;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.Item;


public class AddCommandParser {
    public static AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(CashierUi.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String category = argMultimap.getValue(PREFIX_CATEGORY).get();
        String amountString = argMultimap.getValue(PREFIX_AMOUNT).get();
        double amount = Double.parseDouble(amountString);
        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        int quantity = Integer.parseInt(quantityString);
        Item item = new Item(description, category, quantity, amount);
        AddCommand addCommand = new AddCommand(item);
        return addCommand;
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}

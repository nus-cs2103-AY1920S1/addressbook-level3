package seedu.address.cashier.logic;

import static seedu.address.cashier.logic.AddCommandParser.arePrefixesPresent;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_INDEX;
import static seedu.address.cashier.logic.CliSyntax.PREFIX_QUANTITY;

import seedu.address.cashier.commands.EditCommand;
import seedu.address.cashier.logic.exception.NotANumberException;
import seedu.address.cashier.logic.exception.ParseException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.ui.CashierMessages;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws NotANumberException if the user input is not a number
     * @throws ParseException if the user input does not conform the expected format
     */
    public static EditCommand parse(String args, ModelManager modelManager) throws NotANumberException, ParseException {
        int index;
        int quantity;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(CashierMessages.MESSAGE_INVALID_EDITCOMMAND_FORMAT);
        }

        String indexString = argMultimap.getValue(PREFIX_INDEX).get();
        try {
            index = Integer.parseInt(indexString);
        } catch (Exception e) {
            throw new NotANumberException(CashierMessages.INDEX_NOT_A_NUMBER);
        }

        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            throw new NotANumberException(CashierMessages.QUANTITY_NOT_A_NUMBER);
        }

        return new EditCommand(index, quantity);
    }

}

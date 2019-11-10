package seedu.address.logic.parser.currency;

import seedu.address.logic.commands.currency.CancelEditCurrencyCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code CancelEditCurrencyCommand}.
 */
public class CancelEditCurrencyParser implements Parser<CancelEditCurrencyCommand> {
    @Override
    public CancelEditCurrencyCommand parse(String userInput) throws ParseException {
        return new CancelEditCurrencyCommand();
    }
}

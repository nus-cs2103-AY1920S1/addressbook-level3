package seedu.address.logic.parser.currency;

import seedu.address.logic.commands.currency.EnterCreateCurrencyCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterCreateCurrencyCommand}.
 */
public class EnterCreateCurrencyParser implements Parser<EnterCreateCurrencyCommand> {
    @Override
    public EnterCreateCurrencyCommand parse(String args) throws ParseException {
        return new EnterCreateCurrencyCommand();
    }
}

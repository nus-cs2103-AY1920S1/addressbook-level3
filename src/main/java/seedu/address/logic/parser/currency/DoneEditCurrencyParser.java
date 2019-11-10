package seedu.address.logic.parser.currency;

import seedu.address.logic.commands.currency.DoneEditCurrencyCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code DoneEditCurrencyCommand}.
 */
public class DoneEditCurrencyParser implements Parser<DoneEditCurrencyCommand> {
    @Override
    public DoneEditCurrencyCommand parse(String userInput) throws ParseException {
        return new DoneEditCurrencyCommand();
    }
}

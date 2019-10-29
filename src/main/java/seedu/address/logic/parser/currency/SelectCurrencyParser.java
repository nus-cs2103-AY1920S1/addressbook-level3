package seedu.address.logic.parser.currency;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.currency.SelectCurrencyCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code SelectCurrencyCommand}.
 */
public class SelectCurrencyParser implements Parser<SelectCurrencyCommand> {
    @Override
    public SelectCurrencyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCurrencyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCurrencyCommand.MESSAGE_USAGE), pe);
        }
    }
}

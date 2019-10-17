package seedu.address.logic.parser.expense;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expenditure.EnterEditExpenditureCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to return a {@code EnterEditExpenditureCommand}.
 */
public class EnterEditExpenditureParser implements Parser<EnterEditExpenditureCommand> {
    @Override
    public EnterEditExpenditureCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterEditExpenditureCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEditExpenditureCommand.MESSAGE_USAGE), pe);
        }
    }
}

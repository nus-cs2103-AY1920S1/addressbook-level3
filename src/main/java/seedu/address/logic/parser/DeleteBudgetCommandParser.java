package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.budget.DeleteBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteBudgetCommandParser implements Parser<DeleteBudgetCommand> {

    public DeleteBudgetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteBudgetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE), pe);
        }
    }

}

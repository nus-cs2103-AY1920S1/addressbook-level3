package seedu.moolah.logic.parser.budget;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.budget.DeleteBudgetByIndexCommand;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteBudgetByIndexCommand object.
 */
public class DeleteBudgetByIndexCommandParser implements Parser<DeleteBudgetByIndexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBudgetByIndexCommand
     * and returns a DeleteBudgetByIndexCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBudgetByIndexCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteBudgetByIndexCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetByIndexCommand.MESSAGE_USAGE), pe);
        }
    }

}

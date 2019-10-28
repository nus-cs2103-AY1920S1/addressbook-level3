package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewBudgetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewBudgetCommand object
 */
public class ViewBudgetCommandParser implements Parser<ViewBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewBudgetCommand
     * and returns a ViewBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewBudgetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewBudgetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewBudgetCommand.MESSAGE_USAGE), pe);
        }
    }

}

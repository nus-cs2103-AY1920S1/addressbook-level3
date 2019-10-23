package seedu.travezy.logic.parser;

import seedu.travezy.logic.commands.GoToCommand;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.ui.PageType;
import seedu.travezy.commons.core.Messages;

/**
 * A parser to parse user input and create a GoToCommand object.
 */
public class GoToParser implements Parser<GoToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCommand
     * and returns an GoToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoToCommand parse(String args) throws ParseException {
        try {
            PageType pageType = ParserUtil.parsePageType(args);
            return new GoToCommand(pageType);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE), pe);
        }
    }
}

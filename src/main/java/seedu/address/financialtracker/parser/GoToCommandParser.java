package seedu.address.financialtracker.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.financialtracker.commands.GoToCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.PageType;

public class GoToCommandParser implements Parser<GoToCommand> {

    public GoToCommand parse(String args) throws ParseException {
        try {
            PageType pageType = ParserUtil.parsePageType(args);
            return new GoToCommand(pageType);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE), pe);
        }
    }
}

package seedu.travezy.financialtracker.parser;

import seedu.travezy.financialtracker.commands.GoToCommand;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.ui.PageType;
import seedu.travezy.commons.core.Messages;

/**
 * Parser for GoToCommand to understand which pages user what to switch to.
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    /**
     * Parsing the user input.
     * @param args the user input
     * @return a GotoCommand with specified page to switch
     * @throws ParseException when the format is wrong / pageType isnt found
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

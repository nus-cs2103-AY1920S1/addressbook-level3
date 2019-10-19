package seedu.ichifund.logic.parser.loans;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.parser.ParserManager;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the loans feature.
 */
public class LoansParserManager implements ParserManager {

    private final int tabIndex = 3;

    @Override
    public String getTabSwitchCommandWord() {
        return "loans";
    }

    @Override
    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch(commandWord) {
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

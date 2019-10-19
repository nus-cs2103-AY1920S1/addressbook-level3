package seedu.ichifund.logic.parser.analytics;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.parser.ParserManager;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the analytics feature.
 */
public class AnalyticsParserManager implements ParserManager {

    private final int tabIndex = 4;

    @Override
    public String getTabSwitchCommandWord() {
        return "analytics";
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

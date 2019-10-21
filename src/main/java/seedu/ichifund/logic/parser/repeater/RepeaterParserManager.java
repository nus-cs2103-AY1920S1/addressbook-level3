package seedu.ichifund.logic.parser.repeater;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.repeater.AddRepeaterCommand;
import seedu.ichifund.logic.parser.ParserManager;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the repeater feature.
 */
public class RepeaterParserManager implements ParserManager {

    private final int tabIndex = 1;

    @Override
    public String getTabSwitchCommandWord() {
        return "rep";
    }

    @Override
    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch(commandWord) {

        case AddRepeaterCommand.COMMAND_WORD:
            return new AddRepeaterCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

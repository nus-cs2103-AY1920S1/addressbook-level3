package seedu.ichifund.logic.parser;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.parser.exceptions.ParseException;

public interface ParserManager {

    String getTabSwitchCommandWord();

    int getTabIndex();

    Command parseCommand(String commandWord, String arguments) throws ParseException;
}

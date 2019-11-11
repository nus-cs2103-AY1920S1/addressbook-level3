package seedu.ichifund.logic.parser;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Represents a FeatureParser that passes user input to the appropriate Parser.
 */
public interface FeatureParser {

    String getTabSwitchCommandWord();

    int getTabIndex();

    Command parseCommand(String commandWord, String arguments) throws ParseException;
}

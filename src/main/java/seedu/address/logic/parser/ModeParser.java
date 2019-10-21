package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

/**
 * Parses user input.
 */
public abstract class ModeParser {

    public abstract Command parseCommand(String userInput) throws ParseException;

    public List<AutoFillAction> getAutoFill(String input) {
        return new ArrayList<>();
    }
}

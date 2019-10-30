package seedu.address.logic.parser;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchCommand object
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TogglePanelCommand
     * and returns a TogglePanelCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SwitchCommand parse(String args) throws ParseException {
        return new SwitchCommand();
    }
}

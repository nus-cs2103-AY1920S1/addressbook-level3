package seedu.address.logic.parser.settings;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.settingcommands.HintsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HintsCommand object
 */
public class HintsCommandParser implements Parser<HintsCommand> {

    @Override
    public HintsCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HintsCommand.MESSAGE_USAGE));
        }
        boolean isEnabled;
        switch (trimmedArgs.toUpperCase()) {
        case "ON":
            isEnabled = true;
            break;
        case "OFF":
            isEnabled = false;
            break;
        default:
            throw new ParseException("No such option: " + trimmedArgs.toUpperCase());
        }
        return new HintsCommand(isEnabled);
    }
}

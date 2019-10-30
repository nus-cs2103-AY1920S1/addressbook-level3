package seedu.address.logic.parser.settings;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.settingcommands.AvatarCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GuessCommand object
 */
public class AvatarCommandParser implements Parser<AvatarCommand> {

    @Override
    public AvatarCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AvatarCommand.MESSAGE_USAGE));
        }
        int id;
        try {
            id = Integer.parseInt(trimmedArgs);
        } catch (NumberFormatException ex) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AvatarCommand.MESSAGE_USAGE));
        }
        if ((id >= 0 && id <= 151) || id == 29126) {
            return new AvatarCommand(id);
        } else {
            throw new ParseException("No such id: " + trimmedArgs);
        }
    }
}

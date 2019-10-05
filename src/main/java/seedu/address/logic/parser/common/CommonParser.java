package seedu.address.logic.parser.common;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

public class CommonParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterPrefsCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        CommonCommand commandType;
        try {
            commandType = CommonCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case PREFS:
            return new EnterPrefsCommand();
        case HELP:
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}

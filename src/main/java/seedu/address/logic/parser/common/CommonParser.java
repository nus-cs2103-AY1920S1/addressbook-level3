package seedu.address.logic.parser.common;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.common.ExitCommand;
import seedu.address.logic.commands.common.HelpCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands that work regardless of the current page of the application.
 */
public class CommonParser implements PageParser<Command> {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + HelpCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        CommonCommand commandType;
        try {
            commandType = CommonCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case HELP:
            return new HelpCommand();
        case EXIT:
            return new ExitCommand();
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}

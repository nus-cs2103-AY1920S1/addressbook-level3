package seedu.address.calendar.logic.commands;

import seedu.address.calendar.logic.parser.Option;
import seedu.address.logic.commands.exceptions.CommandException;

public class AlternativeCommandUtil {
    static final String MESSAGE_INVALID_INPUT = "The command is invalid.";
    static final String MESSAGE_COMMAND_NOT_EXECUTED = "Noted.";

    static void isValidUserCommand(Option option, boolean isBinary) throws CommandException {
        if (isBinary) {
            isValidBinaryCommand(option);
        } else {
            isValidMultiCommand(option);
        }
    }

    private static void isValidBinaryCommand(Option option) throws CommandException {
        if (!option.isBinary()) {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }
    }

    private static void isValidMultiCommand(Option option) throws CommandException {
        if (option.isBinary()) {
            boolean isExecute = option.getBinaryOption();

            if (isExecute) {
                throw new CommandException(MESSAGE_INVALID_INPUT);
            }
        }
    }
}

package seedu.address.calendar.logic.commands;

import seedu.address.calendar.logic.parser.Option;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Handles all alternative command manipulation.
 */
public class AlternativeCommandUtil {
    static final String MESSAGE_INVALID_INPUT = "The command is invalid.";
    static final String MESSAGE_COMMAND_NOT_EXECUTED = "Noted.";

    /**
     * Checks if the user command is valid.
     *
     * @param option The options available to the user
     * @param isBinary Checks if the option is valid
     * @throws CommandException If the command cannot be executed successfully
     */
    static void isValidUserCommand(Option option, boolean isBinary) throws CommandException {
        if (isBinary) {
            isValidBinaryCommand(option);
        } else {
            isValidMultiCommand(option);
        }
    }

    /**
     * Checks if the option is a binary choice (i.e. yes or no).
     *
     * @param option The option specified
     * @throws CommandException If the command cannot be executed successfully
     */
    private static void isValidBinaryCommand(Option option) throws CommandException {
        if (!option.isBinary()) {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }
    }

    /**
     * Checks if the command is valid.
     *
     * @param option The option specified by the user
     * @throws CommandException If the command cannot be successfully executed
     */
    private static void isValidMultiCommand(Option option) throws CommandException {
        if (option.isBinary()) {
            boolean isExecute = option.getBinaryOption();

            if (isExecute) {
                throw new CommandException(MESSAGE_INVALID_INPUT);
            }
        }
    }
}

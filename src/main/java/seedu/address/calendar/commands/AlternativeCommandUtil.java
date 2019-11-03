package seedu.address.calendar.commands;

import seedu.address.calendar.parser.Option;
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

    /*
    public static CommandResult execute(Calendar calendar, Option option, List<AlternativeCommand> commands)
            throws CommandException {
        CommandResult commandResult;

        if (option.isBinary()) {
            commandResult = execute(calendar, option.getBinaryOption(), commands);
        } else {
            commandResult = execute(calendar, option.getNumber(), commands);
        }

        return commandResult;
    }

    private static CommandResult execute(Calendar calendar, boolean isYes, List<AlternativeCommand> commands)
            throws CommandException {
        if (!isYes) {
            return new CommandResult(MESSAGE_COMMAND_NOT_EXECUTED);
        }

        if (commands.size() > 1) {
            throw new CommandException(MESSAGE_INVALID_INPUT); // it was not a yes or no option
        }

        return commands.get(0).execute(calendar);
    }

    private static CommandResult execute(Calendar calendar, int option, List<AlternativeCommand> commands)
            throws CommandException {
        return commands.get(option).execute(calendar);
    }
     */
}

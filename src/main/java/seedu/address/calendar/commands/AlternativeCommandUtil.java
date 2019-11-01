package seedu.address.calendar.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.parser.Option;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.List;

public class AlternativeCommandUtil {
    private static final String MESSAGE_INVALID_INPUT = "The input keyed is invalid.";
    private static final String MESSAGE_COMMAND_NOT_EXECUTED = "Noted.";

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
}

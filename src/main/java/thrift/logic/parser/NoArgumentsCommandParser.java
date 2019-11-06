package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import thrift.logic.commands.Command;
import thrift.logic.commands.ExitCommand;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new Command object. E.g. UndoCommand
 */
public class NoArgumentsCommandParser implements Parser<Command> {

    private String command;
    private String errorMessage;

    public NoArgumentsCommandParser(String command, String errorMessage) {
        requireAllNonNull(command, errorMessage);
        assert !command.equals("") && !errorMessage.equals("") : "Invalid command or error message";
        this.command = command;
        this.errorMessage = errorMessage;
    }

    @Override
    public Command parse(String userInput) throws ParseException {
        if (userInput == null || userInput.trim().equals("")) {
            switch (command) {
            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();
            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
    }
}

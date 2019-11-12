package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddFileCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DecryptFileCommand;
import seedu.address.logic.commands.EncryptFileCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindFileCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListFileCommand;
import seedu.address.logic.commands.MoveFileCommand;
import seedu.address.logic.commands.PreviewFileCommand;
import seedu.address.logic.commands.RemoveFileCommand;
import seedu.address.logic.commands.RenameFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for file book.
 */
public class FileBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final String password;

    /**
     * Initialises a FileBookParser with a given password for file encryption.
     * @param password
     */
    public FileBookParser(String password) {
        this.password = password;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case EncryptFileCommand.COMMAND_WORD:
            return new EncryptFileCommandParser().parse(arguments, password);

        case DecryptFileCommand.COMMAND_WORD:
            return new DecryptFileCommandParser().parse(arguments, password);

        case PreviewFileCommand.COMMAND_WORD:
            return new PreviewFileCommandParser().parse(arguments, password);

        case AddFileCommand.COMMAND_WORD:
            return new AddFileCommandParser().parse(arguments);

        case RemoveFileCommand.COMMAND_WORD:
            return new RemoveFileCommandParser().parse(arguments);

        case FindFileCommand.COMMAND_WORD:
            return new FindFileCommandParser().parse(arguments);

        case RenameFileCommand.COMMAND_WORD:
            return new RenameFileCommandParser().parse(arguments);

        case MoveFileCommand.COMMAND_WORD:
            return new MoveFileCommandParser().parse(arguments);

        case GoToCommand.COMMAND_WORD:
            return new GoToCommandParser().parse(arguments);

        case ListFileCommand.COMMAND_WORD:
            return new ListFileCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

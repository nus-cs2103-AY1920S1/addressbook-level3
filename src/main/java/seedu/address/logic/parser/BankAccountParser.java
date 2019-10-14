package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InCommand;
// import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SplitCommand or InCommand object
 */
public class BankAccountParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the SplitCommand or InCommand
     * and returns an SplitCommand or InCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch(commandWord) {
        // case SplitCommand.COMMAND_WORD:
        //    return new SplitCommand(arguments);
        case InCommand.COMMAND_WORD:
            return new InCommandParser().parse(arguments);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

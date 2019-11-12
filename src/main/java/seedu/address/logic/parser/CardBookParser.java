package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CopyCardNumberCommand;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ReadCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CardBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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

        case AddCardCommand.COMMAND_WORD:
            return new AddCardCommandParser().parse(arguments);

        case DeleteCardCommand.COMMAND_WORD:
            return new DeleteCardCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case GoToCommand.COMMAND_WORD:
            return new GoToCommandParser().parse(arguments);

        case ReadCardCommand.COMMAND_WORD:
            return new ReadCardCommandParser().parse(arguments);

        case CopyCardNumberCommand.COMMAND_WORD:
            return new CopyCardNumberCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

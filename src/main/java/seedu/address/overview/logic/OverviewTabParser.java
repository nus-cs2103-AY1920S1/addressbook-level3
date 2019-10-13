package seedu.address.overview.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.overview.commands.Command;
import seedu.address.overview.commands.NotifyCommand;
import seedu.address.overview.commands.SetCommand;
import seedu.address.overview.logic.exception.ParseException;
import seedu.address.overview.ui.OverviewMessages;

/**
 * Parses user input.
 */
public class OverviewTabParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format.
     * */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case SetCommand.COMMAND_WORD:
            return new SetCommandParser().parse(arguments);

        case NotifyCommand.COMMAND_WORD:
            return new NotifyCommandParser().parse(arguments);

        default:
            throw new ParseException(OverviewMessages.MESSAGE_NO_SUCH_COMMAND);

        }
    }
}

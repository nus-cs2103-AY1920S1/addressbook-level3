package seedu.travezy.calendar.parser;

import seedu.travezy.calendar.commands.ShowCommand;
import seedu.travezy.calendar.commands.Command;
import seedu.travezy.address.logic.commands.HelpCommand;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.commons.core.Messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarParser {
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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch(commandWord) {

        case ShowCommand.COMMAND_WORD:
            return new ShowParser().parse(arguments);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

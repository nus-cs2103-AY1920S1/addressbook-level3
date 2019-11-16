package seedu.ifridge.logic.parser.wastelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.wastelist.FeedbackWasteCommand;
import seedu.ifridge.logic.commands.wastelist.ListWasteCommand;
import seedu.ifridge.logic.commands.wastelist.ReportWasteCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Waste List Parser to parse the commands that pertains to the waste list.
 */
public class WasteListParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final String LIST_TYPE_WORD = "wlist";
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
        case FeedbackWasteCommand.COMMAND_WORD:
            return new FeedbackWasteCommand();

        case ListWasteCommand.COMMAND_WORD:
            return new ListWasteCommandParser().parse(arguments);

        case ReportWasteCommand.COMMAND_WORD:
            return new ReportWasteCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

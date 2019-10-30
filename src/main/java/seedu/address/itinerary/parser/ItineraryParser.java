package seedu.address.itinerary.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.itinerary.commands.AddEventCommand;
import seedu.address.itinerary.commands.ClearEventCommand;
import seedu.address.itinerary.commands.DeleteEventCommand;
import seedu.address.itinerary.commands.DoneEventCommand;
import seedu.address.itinerary.commands.EditCommand;
import seedu.address.itinerary.commands.GreetCommand;
import seedu.address.itinerary.commands.HelpCommand;
import seedu.address.itinerary.commands.HistoryCommand;
import seedu.address.itinerary.commands.ListCommand;
import seedu.address.itinerary.commands.SearchCommand;
import seedu.address.itinerary.commands.SortCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.GoToParser;
import seedu.address.commons.core.Messages;

/**
 * Parses user input.
 */
public class ItineraryParser {

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case GreetCommand.COMMAND_WORD:
            return new GreetCommand();

        case ClearEventCommand.COMMAND_WORD:
            return new ClearEventCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case GoToCommand.COMMAND_WORD:
            return new GoToParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case DoneEventCommand.COMMAND_WORD:
            return new DoneEventCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        default:
            throw new ParseException("I know you like travelling but the input command is out of this "
                        + "world! :3");
        }
    }

}

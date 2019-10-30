package dukecooks.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.Command;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.ExitCommand;
import dukecooks.logic.commands.FindCommand;
import dukecooks.logic.commands.HelpCommand;
import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.dashboard.DoneTaskCommand;
import dukecooks.logic.commands.stats.StatisticsCommand;
import dukecooks.logic.parser.dashboard.DoneTaskCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class DukeCooksParser {

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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FillCommand;
import seedu.address.logic.commands.FindIncidentsCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.FindVehiclesCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListIncidentsCommand;
import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.logic.commands.ListVehiclesCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.SubmitCommand;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class IncidentManagerParser {

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case FindIncidentsCommand.COMMAND_WORD:
            return new FindIncidentsCommandParser().parse(arguments);

        case FindVehiclesCommand.COMMAND_WORD:
            return new FindVehiclesCommandParser().parse(arguments);

        case ListPersonsCommand.COMMAND_WORD:
            return new ListPersonsCommand();

        case ListIncidentsCommand.COMMAND_WORD:
            return new ListIncidentsCommand();

        case ListVehiclesCommand.COMMAND_WORD:
            return new ListVehiclesCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case SwapCommand.COMMAND_WORD:
            return new SwapCommand();

        case SubmitCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new SubmitCommand();
            } else {
                return new SubmitCommandParser().parse(arguments);
            }

        case FillCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return new FillCommand(); // TODO remove nulls by merging / inheriting commands
            } else {
                return new FillCommandParser().parse(arguments);
            }

        case NewCommand.COMMAND_WORD:
            return new NewCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

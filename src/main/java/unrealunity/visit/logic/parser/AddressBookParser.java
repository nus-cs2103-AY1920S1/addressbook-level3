package unrealunity.visit.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.logic.commands.AddCommand;
import unrealunity.visit.logic.commands.AddVisitCommand;
import unrealunity.visit.logic.commands.AliasCommand;
import unrealunity.visit.logic.commands.AliasListCommand;
import unrealunity.visit.logic.commands.ClearCommand;
import unrealunity.visit.logic.commands.Command;
import unrealunity.visit.logic.commands.DeleteAppointmentCommand;
import unrealunity.visit.logic.commands.DeleteCommand;
import unrealunity.visit.logic.commands.DeleteVisitCommand;
import unrealunity.visit.logic.commands.EditCommand;
import unrealunity.visit.logic.commands.EditVisitCommand;
import unrealunity.visit.logic.commands.ExitCommand;
import unrealunity.visit.logic.commands.FindCommand;
import unrealunity.visit.logic.commands.FollowUpCommand;
import unrealunity.visit.logic.commands.HelpCommand;
import unrealunity.visit.logic.commands.ListCommand;
import unrealunity.visit.logic.commands.ProfileCommand;
import unrealunity.visit.logic.commands.ReminderCommand;
import unrealunity.visit.logic.commands.ShowCommand;
import unrealunity.visit.logic.commands.SortAppointmentsCommand;
import unrealunity.visit.logic.commands.UnaliasCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

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

        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new ListCommand();

        case AddVisitCommand.COMMAND_WORD:
            return new AddVisitCommandParser().parse(arguments);

        case DeleteVisitCommand.COMMAND_WORD:
            return new DeleteVisitCommandParser().parse(arguments);

        case EditVisitCommand.COMMAND_WORD:
            return new EditVisitCommandParser().parse(arguments);

        case ProfileCommand.COMMAND_WORD:
            return new ProfileCommandParser().parse(arguments);

        case FollowUpCommand.COMMAND_WORD:
            return new FollowUpCommandParser().parse(arguments);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case SortAppointmentsCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new SortAppointmentsCommand();

        case ExitCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new HelpCommand();

        case ShowCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new ShowCommand();

        case AliasListCommand.COMMAND_WORD:
            if (!arguments.trim().isEmpty()) {
                throw new ParseException(Messages.MESSAGE_INVALID_ARGUMENTS);
            }
            return new AliasListCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

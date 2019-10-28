package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.LogOutCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddProjectCommand.COMMAND_WORD:
            return new AddProjectCommandParser().parse(arguments);

        case AddProjectMeetingCommand.COMMAND_WORD:
            return new AddProjectMeetingCommandParser().parse(arguments);

        case AddProfilePictureCommand.COMMAND_WORD:
            return new AddProfilePictureCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddMemberCommand.COMMAND_WORD:
            return new AddMemberCommandParser().parse(arguments);

        case AddFromContactsCommand.COMMAND_WORD:
            return new AddFromContactsCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case AddSpendingCommand.COMMAND_WORD:
            return new AddSpendingCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case RemoveMemberCommand.COMMAND_WORD:
            return new RemoveMemberCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListBudgetCommand.COMMAND_WORD:
            return new ListBudgetCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case CheckoutCommand.COMMAND_WORD:
            return new CheckoutCommandParser().parse(arguments);

        case GenerateSlotCommand.COMMAND_WORD:
            return new GenerateSlotCommandParser().parse(arguments);

        case SendMailCommand.COMMAND_WORD:
            return new SendMailCommandParser().parse(arguments);

        case BroadcastMailCommand.COMMAND_WORD:
            return new BroadcastMailCommandParser().parse(arguments);

        case SignInCommand.COMMAND_WORD:
            return new SignInCommandParser().parse(arguments);

        case LogOutCommand.COMMAND_WORD:
            return new LogOutCommandParser().parse(arguments);

        case SortTaskCommand.COMMAND_WORD:
            return new SortTaskParser().parse(arguments);
            
        case AddTimetableCommand.COMMAND_WORD:
            return new AddTimetableCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

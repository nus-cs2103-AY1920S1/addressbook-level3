package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddDriverCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteIdCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.commands.EditDriverCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCustomerCommand;
import seedu.address.logic.commands.FindDriverCommand;
import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.commands.GoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ReadIdCommand;
import seedu.address.logic.commands.SavePdfCommand;
import seedu.address.logic.commands.SuggestCommand;
import seedu.address.logic.commands.ViewCustomerTaskCommand;
import seedu.address.logic.commands.ViewCustomerWindowCommand;
import seedu.address.logic.commands.ViewDriverTaskCommand;

import seedu.address.logic.commands.ViewDriverWindowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        case AddCustomerCommand.COMMAND_WORD:
            return new AddCustomerCommandParser().parse(arguments);

        case AddDriverCommand.COMMAND_WORD:
            return new AddDriverCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case EditCustomerCommand.COMMAND_WORD:
            return new EditCustomerCommandParser().parse(arguments);

        case EditDriverCommand.COMMAND_WORD:
            return new EditDriverCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case AssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(arguments);

        case SuggestCommand.COMMAND_WORD:
            return new SuggestCommandParser().parse(arguments);

        case FreeCommand.COMMAND_WORD:
            return new FreeCommandParser().parse(arguments);

        case DeleteIdCommand.COMMAND_WORD:
            return new DeleteIdCommandParser().parse(arguments);

        case ReadIdCommand.COMMAND_WORD:
            return new ReadIdCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case SavePdfCommand.COMMAND_WORD:
            return new SavePdfCommandParser().parse(arguments);

        case FindCustomerCommand.COMMAND_WORD:
            return new FindCustomerCommandParser().parse(arguments);

        case FindDriverCommand.COMMAND_WORD:
            return new FindDriverCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case ViewCustomerTaskCommand.COMMAND_WORD:
            return new ViewCustomerTaskCommandParser().parse(arguments);

        case ViewDriverTaskCommand.COMMAND_WORD:
            return new ViewDriverTaskCommandParser().parse(arguments);

        case GoCommand.COMMAND_WORD:
            return new GoCommandParser().parse(arguments);

        case ViewCustomerWindowCommand.COMMAND_WORD:
            return new ViewCustomerWindowCommandParser().parse(arguments);

        case ViewDriverWindowCommand.COMMAND_WORD:
            return new ViewDriverWindowCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

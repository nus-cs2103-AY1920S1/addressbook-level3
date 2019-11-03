package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.allocate.AutoAllocateCommand;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.logic.commands.allocate.ManualAllocateCommand;
import seedu.address.logic.commands.employee.AddCommand;
import seedu.address.logic.commands.employee.ClearCommand;
import seedu.address.logic.commands.employee.DeleteCommand;
import seedu.address.logic.commands.employee.EditCommand;
import seedu.address.logic.commands.employee.FetchEmployeeCommand;
import seedu.address.logic.commands.employee.FindByTagCommand;
import seedu.address.logic.commands.employee.FindCommand;
import seedu.address.logic.commands.employee.ListCommand;
import seedu.address.logic.commands.employee.ListEmployeeCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.AssignDateCommand;
import seedu.address.logic.commands.event.ClearDateMappingCommand;
import seedu.address.logic.commands.event.DeleteDateMappingCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.FetchEventCommand;
import seedu.address.logic.commands.event.FindEventByTagCommand;
import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.finance.Pay;
import seedu.address.logic.commands.finance.PaySlip;
import seedu.address.logic.commands.finance.Undopay;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.MainCommand;
import seedu.address.logic.commands.general.ScheduleCommand;
import seedu.address.logic.commands.general.SetAppDateCommand;
import seedu.address.logic.commands.schedule.DisplayScheduleBetweenCommand;
import seedu.address.logic.commands.schedule.DisplayScheduleForDateCommand;
import seedu.address.logic.commands.schedule.DisplayScheduleForYearMonthCommand;
import seedu.address.logic.commands.schedule.GenerateScheduleCommand;
import seedu.address.logic.commands.statistics.StatisticsCommand;
import seedu.address.logic.parser.allocate.AutoAllocateCommandParser;
import seedu.address.logic.parser.allocate.DeallocateCommandParser;
import seedu.address.logic.parser.allocate.ManualAllocateCommandParser;
import seedu.address.logic.parser.employee.AddCommandParser;
import seedu.address.logic.parser.employee.DeleteCommandParser;
import seedu.address.logic.parser.employee.EditCommandParser;
import seedu.address.logic.parser.employee.FetchEmployeeCommandParser;
import seedu.address.logic.parser.employee.FindByTagCommandParser;
import seedu.address.logic.parser.employee.FindCommandParser;
import seedu.address.logic.parser.event.AddEventCommandParser;
import seedu.address.logic.parser.event.AssignDateCommandParser;
import seedu.address.logic.parser.event.ClearDateMappingCommandParser;
import seedu.address.logic.parser.event.DeleteDateMappingCommandParser;
import seedu.address.logic.parser.event.DeleteEventCommandParser;
import seedu.address.logic.parser.event.EditEventCommandParser;
import seedu.address.logic.parser.event.FetchEventCommandParser;
import seedu.address.logic.parser.event.FindEventByTagCommandParser;
import seedu.address.logic.parser.event.FindEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.general.SetAppDateCommandParser;
import seedu.address.logic.parser.schedule.DisplayScheduleBetweenParser;
import seedu.address.logic.parser.finance.CalculatePayCommandParser;
import seedu.address.logic.parser.finance.Payparser;
import seedu.address.logic.parser.finance.Undopayparser;
import seedu.address.logic.parser.schedule.DisplayScheduleForDateParser;
import seedu.address.logic.parser.schedule.DisplayScheduleForYearMonthParser;


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

        case Undopay.COMMAND_WORD:
            return new Undopayparser().parse(arguments);

        case PaySlip.COMMAND_WORD:
            return new CalculatePayCommandParser().parse(arguments);

        case Pay.COMMAND_WORD:
            return new Payparser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case MainCommand.COMMAND_WORD:
            return new MainCommand();

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FetchEventCommand.COMMAND_WORD:
            return new FetchEventCommandParser().parse(arguments);

        case FetchEmployeeCommand.COMMAND_WORD:
            return new FetchEmployeeCommandParser().parse(arguments);

        case DeallocateCommand.COMMAND_WORD:
            return new DeallocateCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommand();

        case DisplayScheduleForDateCommand.COMMAND_WORD:
            return new DisplayScheduleForDateParser().parse(arguments);

        case DisplayScheduleForYearMonthCommand.COMMAND_WORD:
            return new DisplayScheduleForYearMonthParser().parse(arguments);

        case DisplayScheduleBetweenCommand.COMMAND_WORD:
            return new DisplayScheduleBetweenParser().parse(arguments);

        case GenerateScheduleCommand.COMMAND_WORD:
            return new GenerateScheduleCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindByTagCommand.COMMAND_WORD:
            return new FindByTagCommandParser().parse(arguments);

        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

        case FindEventByTagCommand.COMMAND_WORD:
            return new FindEventByTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListEmployeeCommand.COMMAND_WORD:
            return new ListEmployeeCommand();

        case ListEventCommand.COMMAND_WORD:
            return new ListEventCommand();

        case ManualAllocateCommand.COMMAND_WORD:
            return new ManualAllocateCommandParser().parse(arguments);

        case AutoAllocateCommand.COMMAND_WORD:
            return new AutoAllocateCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        case AssignDateCommand.COMMAND_WORD:
            return new AssignDateCommandParser().parse(arguments);

        case DeleteDateMappingCommand.COMMAND_WORD:
            return new DeleteDateMappingCommandParser().parse(arguments);

        case ClearDateMappingCommand.COMMAND_WORD:
            return new ClearDateMappingCommandParser().parse(arguments);

        case SetAppDateCommand.COMMAND_WORD:
            return new SetAppDateCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

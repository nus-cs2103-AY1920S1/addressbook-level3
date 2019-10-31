package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Remove after implementing MeetingTime Parser
import java.time.LocalDateTime;
import java.time.Duration;

import seedu.address.logic.commands.AddCalendarCommand;
import seedu.address.logic.commands.AddInventoryCommand;
import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClockCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteInventoryCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.DoingTaskCommand;
import seedu.address.logic.commands.DoneTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditInventoryCommand;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindMeetingTimeCommand;
import seedu.address.logic.commands.FindMemberCommand;
import seedu.address.logic.commands.FireCommand;
import seedu.address.logic.commands.GetStatisticsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListInventoryCommand;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.commands.SetImageCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ProjectDashboardParser {

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
    public Command parseCommand(String userInput) throws ParseException, FileNotFoundException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        // TASK
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DoingTaskCommand.COMMAND_WORD:
            return new DoingTaskCommandParser().parse(arguments);

        case SetDeadlineCommand.COMMAND_WORD:
            return new SetDeadlineCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        // MEMBER
        case AddMemberCommand.COMMAND_WORD:
            return new AddMemberCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD:
            return new DeleteMemberCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD:
            return new EditMemberCommandParser().parse(arguments);

        case FindMemberCommand.COMMAND_WORD:
            return new FindMemberCommandParser().parse(arguments);

        case ListMemberCommand.COMMAND_WORD:
            return new ListMemberCommand();

        // ASSOCIATION
        case AssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(arguments);

        case FireCommand.COMMAND_WORD:
            return new FireCommandParser().parse(arguments);

        // INVENTORY
        case ListInventoryCommand.COMMAND_WORD:
            return new ListInventoryCommand();

        case AddInventoryCommand.COMMAND_WORD:
            return new AddInventoryCommandParser().parse(arguments);

        case EditInventoryCommand.COMMAND_WORD:
            return new EditInventoryCommandParser().parse(arguments);

        case DeleteInventoryCommand.COMMAND_WORD:
            return new DeleteInventoryCommandParser().parse(arguments);

        //case GeneratePDFCommand.COMMAND_WORD:
            //return new GeneratePDFCommand();

        // STATS
        case GetStatisticsCommand.COMMAND_WORD_MEMBER:
            return new GetStatisticsCommand();

        case GetStatisticsCommand.COMMAND_WORD_TASK:
            return new GetStatisticsCommand();

        // SETTINGS
        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);

        case ClockCommand.COMMAND_WORD:
            return new ClockCommandParser().parse(arguments);

        // UNIVERSAL
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case SettingsCommand.COMMAND_WORD:
            return new SettingsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case SetImageCommand.COMMAND_WORD:
            return new SetImageCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case AddCalendarCommand.COMMAND_WORD:
            return new AddCalendarParser().parse(arguments);

        case FindMeetingTimeCommand.COMMAND_WORD:
            ////Sample Duration
//          LocalDateTime startDate = new LocalDateTime("20191028T000000Z");
//          LocalDateTime endDate = new LocalDateTime("20191101T170000Z");
//          Duration meetingDuration = Duration.ofHours(4);
//            	of(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)
            LocalDateTime startDate = LocalDateTime.parse("2019-10-28T00:00:00");
            LocalDateTime endDate = LocalDateTime.parse("2019-11-01T17:00:00");
            Duration meetingDuration = Duration.ofHours(4);
            return new FindMeetingTimeCommand(startDate, endDate, meetingDuration);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

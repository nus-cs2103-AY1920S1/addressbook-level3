package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.pluswork.logic.commands.calendar.AddCalendarCommand;
import seedu.pluswork.logic.commands.multiline.AddDCommand;
import seedu.pluswork.logic.commands.multiline.AddICommand;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.commands.calendar.AddMeetingCommand;
import seedu.pluswork.logic.commands.member.AddMemberCommand;
import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.commands.member.AssignCommand;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.logic.commands.settings.ClockCommand;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.calendar.DeleteCalendarCommand;
import seedu.pluswork.logic.commands.inventory.DeleteInventoryCommand;
import seedu.pluswork.logic.commands.calendar.DeleteMeetingCommand;
import seedu.pluswork.logic.commands.member.DeleteMemberCommand;
import seedu.pluswork.logic.commands.task.DeleteTaskCommand;
import seedu.pluswork.logic.commands.task.DoingTaskCommand;
import seedu.pluswork.logic.commands.task.DoneTaskCommand;
import seedu.pluswork.logic.commands.inventory.EditInventoryCommand;
import seedu.pluswork.logic.commands.member.EditMemberCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.logic.commands.universal.ExitCommand;
import seedu.pluswork.logic.commands.calendar.FindMeetingTimeCommand;
import seedu.pluswork.logic.commands.member.FindMemberCommand;
import seedu.pluswork.logic.commands.task.FindTaskCommand;
import seedu.pluswork.logic.commands.member.FireCommand;
import seedu.pluswork.logic.commands.inventory.GeneratePDFCommand;
import seedu.pluswork.logic.commands.statistics.GetStatisticsCommand;
import seedu.pluswork.logic.commands.universal.HelpCommand;
import seedu.pluswork.logic.commands.universal.HomeCommand;
import seedu.pluswork.logic.commands.inventory.ListInventoryCommand;
import seedu.pluswork.logic.commands.member.ListMemberCommand;
import seedu.pluswork.logic.commands.task.ListTaskCommand;
import seedu.pluswork.logic.commands.multiline.NoCommand;
import seedu.pluswork.logic.commands.universal.RedoCommand;
import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.commands.member.SetImageCommand;
import seedu.pluswork.logic.commands.settings.SettingsCommand;
import seedu.pluswork.logic.commands.settings.ThemeCommand;
import seedu.pluswork.logic.commands.universal.UndoCommand;
import seedu.pluswork.logic.commands.multiline.YesCommand;
import seedu.pluswork.logic.parser.calendar.AddCalendarCommandParser;
import seedu.pluswork.logic.parser.calendar.AddMeetingCommandParser;
import seedu.pluswork.logic.parser.calendar.DeleteCalendarCommandParser;
import seedu.pluswork.logic.parser.calendar.DeleteMeetingCommandParser;
import seedu.pluswork.logic.parser.calendar.FindMeetingTimeCommandParser;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.logic.parser.inventory.AddInventoryCommandParser;
import seedu.pluswork.logic.parser.inventory.DeleteInventoryCommandParser;
import seedu.pluswork.logic.parser.inventory.EditInventoryCommandParser;
import seedu.pluswork.logic.parser.inventory.GeneratePDFCommandParser;
import seedu.pluswork.logic.parser.member.AddMemberCommandParser;
import seedu.pluswork.logic.parser.member.AssignCommandParser;
import seedu.pluswork.logic.parser.member.DeleteMemberCommandParser;
import seedu.pluswork.logic.parser.member.EditMemberCommandParser;
import seedu.pluswork.logic.parser.member.FindMemberCommandParser;
import seedu.pluswork.logic.parser.member.FireCommandParser;
import seedu.pluswork.logic.parser.member.SetImageCommandParser;
import seedu.pluswork.logic.parser.multiline.AddDCommandParser;
import seedu.pluswork.logic.parser.multiline.AddICommandParser;
import seedu.pluswork.logic.parser.settings.ClockCommandParser;
import seedu.pluswork.logic.parser.settings.ThemeCommandParser;
import seedu.pluswork.logic.parser.task.AddTaskCommandParser;
import seedu.pluswork.logic.parser.task.DeleteTaskCommandParser;
import seedu.pluswork.logic.parser.task.DoingTaskCommandParser;
import seedu.pluswork.logic.parser.task.DoneTaskCommandParser;
import seedu.pluswork.logic.parser.task.EditTaskCommandParser;
import seedu.pluswork.logic.parser.task.FindTaskCommandParser;
import seedu.pluswork.logic.parser.task.SetDeadlineCommandParser;

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

            case FindTaskCommand.COMMAND_WORD:
                return new FindTaskCommandParser().parse(arguments);

            case ListTaskCommand.COMMAND_WORD:
                return new ListTaskCommand();

            case EditTaskCommand.COMMAND_WORD:
                return new EditTaskCommandParser().parse(arguments);

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

            case GeneratePDFCommand.COMMAND_WORD:
                return new GeneratePDFCommandParser().parse(arguments);

            // STATS
            case GetStatisticsCommand.COMMAND_WORD_MEMBER:

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

            case NoCommand.COMMAND_WORD:
                return new NoCommand();

            case YesCommand.COMMAND_WORD:
                return new YesCommand();

            case AddICommand.COMMAND_WORD:
                return new AddICommandParser().parse(arguments);

            case AddDCommand.COMMAND_WORD:
                return new AddDCommandParser().parse(arguments);

            case SetImageCommand.COMMAND_WORD:
                return new SetImageCommandParser().parse(arguments);

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case AddCalendarCommand.COMMAND_WORD:
                return new AddCalendarCommandParser().parse(arguments);

            case DeleteCalendarCommand.COMMAND_WORD:
                return new DeleteCalendarCommandParser().parse(arguments);

            case FindMeetingTimeCommand.COMMAND_WORD:
                return new FindMeetingTimeCommandParser().parse(arguments);

            case AddMeetingCommand.COMMAND_WORD:
                return new AddMeetingCommandParser().parse(arguments);

            case DeleteMeetingCommand.COMMAND_WORD:
                return new DeleteMeetingCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

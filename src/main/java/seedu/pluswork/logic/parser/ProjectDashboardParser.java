package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.pluswork.logic.commands.AddCalendarCommand;
import seedu.pluswork.logic.commands.AddDCommand;
import seedu.pluswork.logic.commands.AddICommand;
import seedu.pluswork.logic.commands.AddInventoryCommand;
import seedu.pluswork.logic.commands.AddMeetingCommand;
import seedu.pluswork.logic.commands.AddMemberCommand;
import seedu.pluswork.logic.commands.AddTaskCommand;
import seedu.pluswork.logic.commands.AssignCommand;
import seedu.pluswork.logic.commands.ClearCommand;
import seedu.pluswork.logic.commands.ClockCommand;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.DeleteCalendarCommand;
import seedu.pluswork.logic.commands.DeleteInventoryCommand;
import seedu.pluswork.logic.commands.DeleteMeetingCommand;
import seedu.pluswork.logic.commands.DeleteMemberCommand;
import seedu.pluswork.logic.commands.DeleteTaskCommand;
import seedu.pluswork.logic.commands.DoingTaskCommand;
import seedu.pluswork.logic.commands.DoneTaskCommand;
import seedu.pluswork.logic.commands.EditInventoryCommand;
import seedu.pluswork.logic.commands.EditMemberCommand;
import seedu.pluswork.logic.commands.EditTaskCommand;
import seedu.pluswork.logic.commands.ExitCommand;
import seedu.pluswork.logic.commands.FindMeetingTimeCommand;
import seedu.pluswork.logic.commands.FindMemberCommand;
import seedu.pluswork.logic.commands.FindTaskCommand;
import seedu.pluswork.logic.commands.FireCommand;
import seedu.pluswork.logic.commands.GeneratePDFCommand;
import seedu.pluswork.logic.commands.GetStatisticsCommand;
import seedu.pluswork.logic.commands.HelpCommand;
import seedu.pluswork.logic.commands.HomeCommand;
import seedu.pluswork.logic.commands.ListInventoryCommand;
import seedu.pluswork.logic.commands.ListMemberCommand;
import seedu.pluswork.logic.commands.ListTaskCommand;
import seedu.pluswork.logic.commands.NoCommand;
import seedu.pluswork.logic.commands.RedoCommand;
import seedu.pluswork.logic.commands.SetDeadlineCommand;
import seedu.pluswork.logic.commands.SetImageCommand;
import seedu.pluswork.logic.commands.SettingsCommand;
import seedu.pluswork.logic.commands.ThemeCommand;
import seedu.pluswork.logic.commands.UndoCommand;
import seedu.pluswork.logic.commands.YesCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;

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
                return new FindCommandParser().parse(arguments);

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

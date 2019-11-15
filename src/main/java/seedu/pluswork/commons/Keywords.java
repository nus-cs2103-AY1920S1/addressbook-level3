package seedu.pluswork.commons;

import java.util.Arrays;
import java.util.List;

import seedu.pluswork.logic.commands.calendar.AddCalendarCommand;
import seedu.pluswork.logic.commands.calendar.AddMeetingCommand;
import seedu.pluswork.logic.commands.calendar.DeleteCalendarCommand;
import seedu.pluswork.logic.commands.calendar.DeleteMeetingCommand;
import seedu.pluswork.logic.commands.calendar.FindMeetingTimeCommand;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.commands.inventory.DeleteInventoryCommand;
import seedu.pluswork.logic.commands.inventory.EditInventoryCommand;
import seedu.pluswork.logic.commands.inventory.GeneratePDFCommand;
import seedu.pluswork.logic.commands.inventory.ListInventoryCommand;
import seedu.pluswork.logic.commands.member.AddMemberCommand;
import seedu.pluswork.logic.commands.member.AssignCommand;
import seedu.pluswork.logic.commands.member.DeleteMemberCommand;
import seedu.pluswork.logic.commands.member.EditMemberCommand;
import seedu.pluswork.logic.commands.member.FindMemberCommand;
import seedu.pluswork.logic.commands.member.FireCommand;
import seedu.pluswork.logic.commands.member.ListMemberCommand;
import seedu.pluswork.logic.commands.member.SetImageCommand;
import seedu.pluswork.logic.commands.settings.ClockCommand;
import seedu.pluswork.logic.commands.settings.SettingsCommand;
import seedu.pluswork.logic.commands.settings.ThemeCommand;
import seedu.pluswork.logic.commands.statistics.GetStatisticsCommand;
import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.commands.task.DeleteTaskCommand;
import seedu.pluswork.logic.commands.task.DoingTaskCommand;
import seedu.pluswork.logic.commands.task.DoneTaskCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.logic.commands.task.FindTaskCommand;
import seedu.pluswork.logic.commands.task.ListTaskCommand;
import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.logic.commands.universal.ExitCommand;
import seedu.pluswork.logic.commands.universal.HelpCommand;
import seedu.pluswork.logic.commands.universal.HomeCommand;
import seedu.pluswork.logic.commands.universal.RedoCommand;
import seedu.pluswork.logic.commands.universal.UndoCommand;

public class Keywords {
    public static List<String> commandList = Arrays.asList(
            // TASK
            AddTaskCommand.COMMAND_WORD,
            DeleteTaskCommand.COMMAND_WORD,
            FindTaskCommand.COMMAND_WORD,
            ListTaskCommand.COMMAND_WORD,
            EditTaskCommand.COMMAND_WORD,
            DoingTaskCommand.COMMAND_WORD,
            SetDeadlineCommand.COMMAND_WORD,
            DoneTaskCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,

            // MEMBER
            AddMemberCommand.COMMAND_WORD,
            DeleteMemberCommand.COMMAND_WORD,
            EditMemberCommand.COMMAND_WORD,
            FindMemberCommand.COMMAND_WORD,
            ListMemberCommand.COMMAND_WORD,
            SetImageCommand.COMMAND_WORD,

            // ASSOCIATION
            FireCommand.COMMAND_WORD,
            AssignCommand.COMMAND_WORD,

            // INVENTORY
            ListInventoryCommand.COMMAND_WORD,
            AddInventoryCommand.COMMAND_WORD,
            EditInventoryCommand.COMMAND_WORD,
            DeleteInventoryCommand.COMMAND_WORD,
            GeneratePDFCommand.COMMAND_WORD,

            //CALENDAR
            AddCalendarCommand.COMMAND_WORD,
            DeleteCalendarCommand.COMMAND_WORD,
            FindMeetingTimeCommand.COMMAND_WORD,
            AddMeetingCommand.COMMAND_WORD,
            DeleteMeetingCommand.COMMAND_WORD,

            // STATS
            GetStatisticsCommand.COMMAND_WORD_MEMBER,
            GetStatisticsCommand.COMMAND_WORD_TASK,

            // SETTINGS
            ThemeCommand.COMMAND_WORD,
            ClockCommand.COMMAND_WORD,
            SettingsCommand.COMMAND_WORD,

            // UNIVERSAL
            ClearCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            HomeCommand.COMMAND_WORD
    );

    public static String getParameters(String commandWord) {
        switch (commandWord) {
        // TASK
        case AddTaskCommand.COMMAND_WORD:
            return commandWord + " " + AddTaskCommand.PREFIX_USAGE;

        case DeleteTaskCommand.COMMAND_WORD:
            return commandWord + " " + DeleteTaskCommand.PREFIX_USAGE;

        case FindTaskCommand.COMMAND_WORD:
            return commandWord + " " + FindTaskCommand.PREFIX_USAGE;

        case ListTaskCommand.COMMAND_WORD:
            return commandWord + " " + ListTaskCommand.PREFIX_USAGE;

        case EditTaskCommand.COMMAND_WORD:
            return commandWord + " " + EditTaskCommand.PREFIX_USAGE;

        case DoingTaskCommand.COMMAND_WORD:
            return commandWord + " " + DoingTaskCommand.PREFIX_USAGE;

        case SetDeadlineCommand.COMMAND_WORD:
            return commandWord + " " + SetDeadlineCommand.PREFIX_USAGE;

        case DoneTaskCommand.COMMAND_WORD:
            return commandWord + " " + DoneTaskCommand.PREFIX_USAGE;

        case UndoCommand.COMMAND_WORD:
            return commandWord + " " + FindTaskCommand.PREFIX_USAGE;

        case RedoCommand.COMMAND_WORD:
            return commandWord + " " + RedoCommand.PREFIX_USAGE;

        // MEMBER
        case AddMemberCommand.COMMAND_WORD:
            return commandWord + " " + AddMemberCommand.PREFIX_USAGE;

        case DeleteMemberCommand.COMMAND_WORD:
            return commandWord + " " + DeleteMemberCommand.PREFIX_USAGE;

        case EditMemberCommand.COMMAND_WORD:
            return commandWord + " " + EditMemberCommand.PREFIX_USAGE;

        case FindMemberCommand.COMMAND_WORD:
            return commandWord + " " + FindMemberCommand.PREFIX_USAGE;

        case ListMemberCommand.COMMAND_WORD:
            return commandWord + " " + ListMemberCommand.PREFIX_USAGE;

        case SetImageCommand.COMMAND_WORD:
            return commandWord + " " + SetImageCommand.PREFIX_USAGE;

        // ASSOCIATION
        case AssignCommand.COMMAND_WORD:
            return commandWord + " " + AssignCommand.PREFIX_USAGE;

        case FireCommand.COMMAND_WORD:
            return commandWord + " " + FireCommand.PREFIX_USAGE;

        // case  RemoveTaskFromMemberCommand.COMMAND_WORD:
        //     return "fire-task ti/ mi/";

        // case  RemoveMemberFromTaskCommand.COMMAND_WORD:
        //     return "fire-member ti/ mi/";

        // INVENTORY
        case ListInventoryCommand.COMMAND_WORD:
            return commandWord + " " + ListInventoryCommand.PREFIX_USAGE;

        case AddInventoryCommand.COMMAND_WORD:
            return commandWord + " " + AddInventoryCommand.PREFIX_USAGE;

        case EditInventoryCommand.COMMAND_WORD:
            return commandWord + " " + EditInventoryCommand.PREFIX_USAGE;

        case DeleteInventoryCommand.COMMAND_WORD:
            return commandWord + " " + DeleteInventoryCommand.PREFIX_USAGE;

        case GeneratePDFCommand.COMMAND_WORD:
            return commandWord + " " + GeneratePDFCommand.PREFIX_USAGE;

        //CALENDAR
        case AddCalendarCommand.COMMAND_WORD:
            return commandWord + " " + AddCalendarCommand.PREFIX_USAGE;

        case DeleteCalendarCommand.COMMAND_WORD:
            return commandWord + " " + DeleteCalendarCommand.PREFIX_USAGE;

        case FindMeetingTimeCommand.COMMAND_WORD:
            return commandWord + " " + FindMeetingTimeCommand.PREFIX_USAGE;

        case AddMeetingCommand.COMMAND_WORD:
            return commandWord + " " + AddMeetingCommand.PREFIX_USAGE;

        case DeleteMeetingCommand.COMMAND_WORD:
            return commandWord + " " + DeleteMeetingCommand.PREFIX_USAGE;

        // STATS
        case GetStatisticsCommand.COMMAND_WORD_MEMBER:
            return commandWord + " " + GetStatisticsCommand.PREFIX_USAGE;

        case GetStatisticsCommand.COMMAND_WORD_TASK:
            return commandWord + " " + GetStatisticsCommand.PREFIX_USAGE;

        // SETTINGS
        case ThemeCommand.COMMAND_WORD:
            return commandWord + " " + ThemeCommand.PREFIX_USAGE;

        case ClockCommand.COMMAND_WORD:
            return commandWord + " " + ClockCommand.PREFIX_USAGE;

        case SettingsCommand.COMMAND_WORD:
            return commandWord + " " + SettingsCommand.PREFIX_USAGE;

        // UNIVERSAL

        case ClearCommand.COMMAND_WORD:
            return commandWord + " " + ClearCommand.PREFIX_USAGE;

        case ExitCommand.COMMAND_WORD:
            return commandWord + " " + ExitCommand.PREFIX_USAGE;

        case HelpCommand.COMMAND_WORD:
            return commandWord + " " + HelpCommand.PREFIX_USAGE;

        case HomeCommand.COMMAND_WORD:
            return commandWord + " " + HomeCommand.PREFIX_USAGE;

        default:
            return commandWord + " ";
        }
    }
}

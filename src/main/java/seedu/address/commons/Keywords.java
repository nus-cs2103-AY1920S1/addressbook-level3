package seedu.address.commons;

import java.util.List;

import seedu.address.logic.commands.*;

public class Keywords {
    public static List<String> commandList = List.of(
        // TASK
        AddTaskCommand.COMMAND_WORD,
        DeleteTaskCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        DoingTaskCommand.COMMAND_WORD,
        SetDeadlineCommand.COMMAND_WORD,
        DoneTaskCommand.COMMAND_WORD,
        ListMemberByTaskCommand.COMMAND_WORD,

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
        //GeneratePDFCommand.COMMAND_WORD,

        //CALENDAR
        AddCalendarCommand.COMMAND_WORD,
        FindMeetingTimeCommand.COMMAND_WORD,

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
        switch(commandWord) {
            // TASK
            case AddTaskCommand.COMMAND_WORD:
                return commandWord + " " + AddTaskCommand.PREFIX_USAGE;

            case DeleteTaskCommand.COMMAND_WORD:
                return commandWord + " " + DeleteTaskCommand.PREFIX_USAGE;

            case FindCommand.COMMAND_WORD:
                return commandWord + " " + FindCommand.PREFIX_USAGE;

            case ListCommand.COMMAND_WORD: 
                return commandWord + " " + ListCommand.PREFIX_USAGE;

            case EditCommand.COMMAND_WORD:
                return commandWord + " " + EditCommand.PREFIX_USAGE;

            case DoingTaskCommand.COMMAND_WORD:
                return commandWord + " " + DoingTaskCommand.PREFIX_USAGE;

            case SetDeadlineCommand.COMMAND_WORD:
                return commandWord + " " + SetDeadlineCommand.PREFIX_USAGE;

            case DoneTaskCommand.COMMAND_WORD:
                return commandWord + " " + DoneTaskCommand.PREFIX_USAGE;
            
            case ListMemberByTaskCommand.COMMAND_WORD:
                return commandWord + " " + ListMemberByTaskCommand.PREFIX_USAGE;

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

            case  FireCommand.COMMAND_WORD:
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

            // case GeneratePDFCommand.COMMAND_WORD:
            //     return commandWord + " " + GeneratePDFCommand.PREFIX_USAGE;

            //CALENDAR
            case AddCalendarCommand.COMMAND_WORD:
                return commandWord + " " + AddCalendarCommand.PREFIX_USAGE;

            case FindMeetingTimeCommand.COMMAND_WORD:
                return commandWord + " " + FindMeetingTimeCommand.PREFIX_USAGE;

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

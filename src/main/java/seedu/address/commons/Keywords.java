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

        // MEMBER
        AddMemberCommand.COMMAND_WORD,
        DeleteMemberCommand.COMMAND_WORD,
        EditMemberCommand.COMMAND_WORD,
        FindMemberCommand.COMMAND_WORD,
        ListMemberCommand.COMMAND_WORD,

        // ASSOCIATION
        AddTaskToMemberCommand.COMMAND_WORD,
        AddMemberToTaskCommand.COMMAND_WORD,
        RemoveTaskFromMemberCommand.COMMAND_WORD,
        RemoveMemberFromTaskCommand.COMMAND_WORD,

        // INVENTORY
        ListInventoryCommand.COMMAND_WORD,
        AddInventoryCommand.COMMAND_WORD,
        EditInventoryCommand.COMMAND_WORD,
        DeleteInventoryCommand.COMMAND_WORD,
        GeneratePDFCommand.COMMAND_WORD,

        // STATS
        GetStatisticsCommand.COMMAND_WORD_MEMBER,
        GetStatisticsCommand.COMMAND_WORD_TASK,

        // SETTINGS
        ThemeCommand.COMMAND_WORD,
        ClockCommand.COMMAND_WORD,

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
                return "add-task tn/ s/ t/";

            case DeleteTaskCommand.COMMAND_WORD:
                return "delete-task ti/";

            case FindCommand.COMMAND_WORD:
                return "find-task ti/";

            // case ListCommand.COMMAND_WORD: default

            case EditCommand.COMMAND_WORD:
                return "edit-task ti/ tn/ t/ s/";

            case DoingTaskCommand.COMMAND_WORD:
                return "doing-task ti/";

            case SetDeadlineCommand.COMMAND_WORD:
                return "set-deadline ti/";

            case DoneTaskCommand.COMMAND_WORD:
                return "set-deadline ti/ /at";


            // MEMBER
            case AddMemberCommand.COMMAND_WORD:
                return "add-member mn/ mi/ mt/";

            case DeleteMemberCommand.COMMAND_WORD:
                return "remove-member {id}";
            case EditMemberCommand.COMMAND_WORD:
                return "edit-member mn/ mi/ mt/";
            case FindMemberCommand.COMMAND_WORD:
                return "find-member {KEYWORD}";

            // case ListMemberCommand.COMMAND_WORD: default

            // ASSOCIATION
            case AddTaskToMemberCommand.COMMAND_WORD:
                return "assign-task ti/ mi/";

            case  AddMemberToTaskCommand.COMMAND_WORD:
                return "assign-member ti/ mi/";

            case  RemoveTaskFromMemberCommand.COMMAND_WORD:
                return "fire-task ti/ mi/";

            case  RemoveMemberFromTaskCommand.COMMAND_WORD:
                return "fire-member ti/ mi/";

            // INVENTORY
            // case ListInventoryCommand.COMMAND_WORD: default

            case AddInventoryCommand.COMMAND_WORD:
                return "add-inv i/ p/ ti/ mi/";

            case EditInventoryCommand.COMMAND_WORD:
                return "edit-inv i/ p/ ti/ mi/";

            case DeleteInventoryCommand.COMMAND_WORD:
                return "delete-inv {index}";

            // case GeneratePDFCommand.COMMAND_WORD: default

            // STATS
            // case  GetStatisticsCommand.COMMAND_WORD_MEMBER: default
            // case GetStatisticsCommand.COMMAND_WORD_TASK:

            // SETTINGS
            case ThemeCommand.COMMAND_WORD:
                return "theme {theme}";

            // case ClockCommand.COMMAND_WORD:

            // UNIVERSAL

            // case ClearCommand.COMMAND_WORD:
            // case ExitCommand.COMMAND_WORD:
            // case HelpCommand.COMMAND_WORD:
            // case HomeCommand.COMMAND_WORD:

            default:
                return commandWord + " ";
        }
    }

}

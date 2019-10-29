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
}
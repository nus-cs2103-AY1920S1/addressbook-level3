package seedu.address.logic.commands;

import java.util.HashSet;

public class CommandMasterList {

    private static HashSet<String> commandHashSet = new HashSet<>();

    public static HashSet getCommandWords() {
        commandHashSet.add(SetClassroomCommand.COMMAND_WORD);
        commandHashSet.add(AddClassroomCommand.COMMAND_WORD);
        commandHashSet.add(ListAssignmentCommand.COMMAND_WORD);
        commandHashSet.add(UpdateGradesCommand.COMMAND_WORD);
        commandHashSet.add(EditAssignmentCommand.COMMAND_WORD);
        commandHashSet.add(AddStudentCommand.COMMAND_WORD);
        commandHashSet.add(EditStudentCommand.COMMAND_WORD);
        commandHashSet.add(DeleteStudentCommand.COMMAND_WORD);
        commandHashSet.add(DeleteAssignmentCommand.COMMAND_WORD);
        commandHashSet.add(ClearCommand.COMMAND_WORD);
        commandHashSet.add(FindStudentCommand.COMMAND_WORD);
        commandHashSet.add(ListStudentCommand.COMMAND_WORD);
        commandHashSet.add(ExitCommand.COMMAND_WORD);
        commandHashSet.add(AddAssignmentCommand.COMMAND_WORD);
        commandHashSet.add(HelpCommand.COMMAND_WORD);
        commandHashSet.add(AddLessonCommand.COMMAND_WORD);
        commandHashSet.add(DeleteLessonCommand.COMMAND_WORD);
        commandHashSet.add(EditLessonCommand.COMMAND_WORD);
        commandHashSet.add(ListLessonCommand.COMMAND_WORD);
        commandHashSet.add(UndoCommand.COMMAND_WORD);
        commandHashSet.add(RedoCommand.COMMAND_WORD);
        commandHashSet.add(GetStudentGradesCommand.COMMAND_WORD);
        commandHashSet.add(UploadPictureCommand.COMMAND_WORD);

        return commandHashSet;
    }
}

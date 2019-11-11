package seedu.address.logic.commands;

import java.util.HashSet;

/**
 * Helper class
 * aggregates all command words
 * and example usages of commands
 */
public class CommandMasterList {

    private static HashSet<String> commandHashSet = new HashSet<>();

    /**
     * contains all usages of commands
     * @return HashSet
     */
    public static HashSet getCommandWords() {
        commandHashSet.add(SetClassroomCommand.COMMAND_WORD + " c/CLASSROOM_NAME");
        commandHashSet.add(AddClassroomCommand.COMMAND_WORD + " c/CLASSROOM_NAME");
        commandHashSet.add(UpdateGradesCommand.COMMAND_WORD + " as/ASSIGNMENT_INDEX s/STUDENT_INDEX g/GRADE");
        commandHashSet.add(UpdateGradesCommand.COMMAND_WORD + " as/ASSIGNMENT_INDEX g/grade");
        commandHashSet.add(EditAssignmentCommand.COMMAND_WORD
                + " ASSIGNMENT_INDEX [as/ASSIGNMENT_NAME] [d/DEADLINE]");
        commandHashSet.add(AddStudentCommand.COMMAND_WORD
                + " n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS "
                + "pp/PARENT_PHONE_NUMBER [m/MEDICAL_CONDITIONS] [t/TAG]");
        commandHashSet.add(EditStudentCommand.COMMAND_WORD + " STUDENT_INDEX [n/NAME] [p/PHONE] "
                + "[e/EMAIL] [a/ADDRESS] [m/MEDICAL_CONDITIONS] [t/TAG]");
        commandHashSet.add(DeleteStudentCommand.COMMAND_WORD + " STUDENT_INDEX");
        commandHashSet.add(DeleteAssignmentCommand.COMMAND_WORD + " ASSIGNMENT_INDEX");
        commandHashSet.add(DeleteClassroomCommand.COMMAND_WORD + " c/CLASS");
        commandHashSet.add(FindStudentCommand.COMMAND_WORD + " STUDENT_NAME");
        commandHashSet.add(FindAssignmentCommand.COMMAND_WORD + " ASSIGNMENT_NAME");
        commandHashSet.add(FindLessonCommand.COMMAND_WORD + "LESSON_KEYWORD");
        commandHashSet.add(AddAssignmentCommand.COMMAND_WORD + " as/ASSIGNMENT d/DEADLINE:dd/MM/yy HHmm");
        commandHashSet.add(AddLessonCommand.COMMAND_WORD + " l/LESSON st/START_TIME et/ET_TIME:dd/MM/yy HHmm");
        commandHashSet.add(DeleteLessonCommand.COMMAND_WORD + " LESSON_INDEX day/DAY_INDEX");
        commandHashSet.add(EditLessonCommand.COMMAND_WORD + " LESSON_INDEX day/DAY_INDEX [l/LESSON_NAME] "
                + "[st/START_TIME] [et/END_TIME]");
        commandHashSet.add(GetStudentGradesCommand.COMMAND_WORD + " STUDENT_INDEX");
        commandHashSet.add(GetUnsubmittedAssignmentsCommand.COMMAND_WORD);
        commandHashSet.add(UploadPictureCommand.COMMAND_WORD + " STUDENT_INDEX");
        commandHashSet.add(ResetDisplayPictureCommand.COMMAND_WORD + " STUDENT_INDEX");
        commandHashSet.add(ListAssignmentCommand.COMMAND_WORD);
        commandHashSet.add(ListStudentCommand.COMMAND_WORD);
        commandHashSet.add(ListLessonCommand.COMMAND_WORD);
        commandHashSet.add(ClearCommand.COMMAND_WORD);
        commandHashSet.add(HelpCommand.COMMAND_WORD);
        commandHashSet.add(UndoCommand.COMMAND_WORD);
        commandHashSet.add(RedoCommand.COMMAND_WORD);
        commandHashSet.add(ExitCommand.COMMAND_WORD);
        return commandHashSet;
    }
}

package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an add student command, specific to a group.
 */
public class GroupAddStudentCommand extends GroupCommand{

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an existing student to an existing group\n"
            + "Parameters:\n"
            + "groupId/ [GROUP_ID]\n"
            + "Example: groupId/G03\n\n"
            + "studentNumber/ [STUDENT_NUMBER]\n"
            + "Example: studentNumber/31 (Specifies the 31st student in the student list)\n\n"
            + "groupIndexNumber/ [INDEX_NUMBER]\n"
            + "Example: groupIndexNumber/ 2 (Specifies the index number in the group to add to)";

    private final String groupId;
    private final int studentNumber;
    private final int groupIndexNumber;

    /**
     * Creates a QuizAddQuestionCommand instance with the appropriate attributes.
     * @param groupId The identifier of the group.
     * @param studentNumber The student's index number.
     */
    public GroupAddStudentCommand(String groupId, int studentNumber, int groupIndexNumber) {
        this.groupId = groupId;
        this.studentNumber = studentNumber;
        this.groupIndexNumber = groupIndexNumber;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addStudentToGroup(groupId, studentNumber,groupIndexNumber);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Added student: " + studentNumber + " to group: " + groupId;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupAddStudentCommand)) {
            return false;
        }

        // state check
        GroupAddStudentCommand e = (GroupAddStudentCommand) other;
        return this.groupId.equals(e.groupId);
    }

}

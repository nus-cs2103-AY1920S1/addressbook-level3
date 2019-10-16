package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupCreateManuallyCommand extends GroupCommand{

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group manually.\n"
            + "Parameters:\n"
            + "manual/\n"
            + "Example: manual/\n\n"
            + "groupId/ [GROUP_ID]\n"
            + "Example: groupId/ G03\n\n"
            + "studentNumber/ [STUDENT_NUMBER]\n"
            + "Example: studentNumber/ 1 3 5 (Adds students 1, 3 and 5 in the student list to the group)\n\n";

    private final String groupId;
    private final ArrayList<Integer> studentNumbers;

    /**
     * Creates a QuizCreateManuallyCommand instance with the appropriate attributes.
     * @param fields The fields to be edited, including quizID and questions.
     */
    public GroupCreateManuallyCommand(HashMap<String, String> fields) {
        String groupId = fields.get("groupID");
        String studentNumbersKey = fields.get("studentNumbers");
        String[] splitStudentNumbers = studentNumbersKey.split(" ");

        ArrayList<Integer> studentNumbers = new ArrayList<>();
        for (String s : splitStudentNumbers) {
            studentNumbers.add(Integer.parseInt(s));
        }

        this.groupId = groupId;
        this.studentNumbers = studentNumbers;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.createGroupManually(groupId, studentNumbers);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        int numStudents = studentNumbers.size();
        if (numStudents == 1) {
            return "Created group: " + groupId + " with " + numStudents + " student.";
        } else {
            return "Created quiz: " + groupId + " with " + studentNumbers.size() + " students.";
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCreateManuallyCommand)) {
            return false;
        }

        // state check
        GroupCreateManuallyCommand e = (GroupCreateManuallyCommand) other;
        return this.groupId.equals(e.groupId);
    }
}

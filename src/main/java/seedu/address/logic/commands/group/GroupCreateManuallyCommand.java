package seedu.address.logic.commands.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents an group create command (manual).
 */
public class GroupCreateManuallyCommand extends GroupCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group manually.\n"
            + "Parameters:\n"
            + "manual/\n"
            + "Example: manual/\n\n"
            + "groupID/ [GROUP_ID]\n"
            + "Example: groupID/ G03\n\n"
            + "studentNumber/ [STUDENT_NUMBER]\n"
            + "Example: studentNumber/ 1 3 5 (Adds students 1, 3 and 5 in the student list to the group)\n"
            + "Full Example: group manual/ groupID/G01 studentNumber/1 2 3 --> adds student 1,2 and 3 to group G01\n\n";

    public static final String CREATED_SUCCESSFULLY_WITH_ONE = "Created group: %1$s with %2$d student.";
    public static final String CREATED_SUCCESSFULLY_WITH_MANY = "Created group: %1$s with %2$d students.";
    public static final String OUT_OF_BOUNDS = "One or more of the student index numbers input are out of bounds";
    private final String groupId;
    private final ArrayList<Integer> studentNumbers;

    /**
     * Creates a GroupCreateManuallyCommand instance with the appropriate attributes.
     *
     * @param fields The fields to be edited, including groupID and students.
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
     * Creates a GroupCreateManuallyCommand instance with given attributes(For Testing)
     */
    public GroupCreateManuallyCommand(String groupId, ArrayList<Integer> studentNumbers) {
        this.groupId = groupId;
        this.studentNumbers = studentNumbers;
    }

    /**
     * Executes the user command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (groupId.isEmpty() || groupId.equals("")) {
            throw new CommandException(GROUP_ID_LEFT_EMPTY);
        }
        if (model.checkGroupExists(groupId)) {
            throw new CommandException(String.format(GROUP_ALREADY_EXISTS, groupId));
        }
        List<Student> lastShownList = model.getFilteredStudentList();
        boolean outOfBounds = false;
        for (Integer i : studentNumbers) {
            if (i < 1 || i > lastShownList.size()) {
                outOfBounds = true;
                break;
            }
        }
        if (outOfBounds) {
            throw new CommandException(OUT_OF_BOUNDS);
        }
        model.createGroupManually(groupId, studentNumbers);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     *
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        int numStudents = studentNumbers.size();
        if (numStudents == 1) {
            return String.format(CREATED_SUCCESSFULLY_WITH_ONE, groupId, 1);
        } else {
            return String.format(CREATED_SUCCESSFULLY_WITH_MANY, groupId, numStudents);
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

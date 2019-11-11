package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Adds a student to the classroom.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the classroom. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_PARENTPHONE + "PARENTPHONE "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_MEDICALCONDITION + "MEDICALCONDITION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_PARENTPHONE + "91234567 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_MEDICALCONDITION + "Sinus "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the classroom.";
    public static final String MESSAGE_SAME_PHONE_AND_PARENT_PHONE = "Phone and parent phone cannot be the same.";

    private final Student toAdd;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        } else if (toAdd.getPhone().toString().equals(toAdd.getParentPhone().toString())) {
            throw new CommandException(MESSAGE_SAME_PHONE_AND_PARENT_PHONE);
        }

        model.addStudent(toAdd);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Assignment> assignmentList = model.getFilteredAssignmentList();
        for (Assignment assignment: assignmentList) {
            if (assignment.isCompleted()) {
                Assignment editedAssignment = new Assignment(assignment.getAssignmentName(),
                        assignment.getAssignmentDeadline());
                editedAssignment.setGrades(assignment.namesStringListFromGrades(),
                        assignment.marksStringListFromGrades());
                editedAssignment.addOneStudentGrade(toAdd.getName().fullName);
                model.setAssignment(assignment, editedAssignment);
            }
        }
        model.saveState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddStudentCommand // instanceof handles nulls
                && toAdd.equals(((AddStudentCommand) other).toAdd));
    }
}

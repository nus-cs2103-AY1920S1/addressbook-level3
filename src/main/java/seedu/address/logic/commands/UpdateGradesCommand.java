package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;


/**
 * Updates the grades of an existing assignment in the classroom.
 */
public class UpdateGradesCommand extends Command {

    public static final String COMMAND_WORD = "grades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the grades of the assignment identified "
                                                   + "by the index number of the assignment list. "
                                                   + "Existing values will be overwritten by the input values.\n"
                                                   + "Parameters: INDEX (must be a positive integer) "
                                                   + "GRADE ... GRADE"
                                                   + "Example: " + COMMAND_WORD + " 1 "
                                                   + "g/ 20 34 56 87";


    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the classroom.";

    private final Index index;
    private final List<String> students;
    private final List<String> marks;

    /**
     * @param index of the assignment in the filtered assignment list to edit
     * @param marks List of marks
     */
    public UpdateGradesCommand(Index index, List<String> marks) {
        requireNonNull(index);
        requireNonNull(marks);

        this.index = index;
        this.students = new ArrayList<String>();
        this.marks = marks;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownAssignmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        if (lastShownStudentList.size() != marks.size()) {
            String tooManyInputsError = "Number of inputs should be " + lastShownStudentList.size();
            tooManyInputsError += " for " + lastShownStudentList.size() + " students";
            throw new CommandException(tooManyInputsError);
        }

        for (Student s : lastShownStudentList) {
            String name = s.getName().fullName;
            students.add(name);
        }

        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        Assignment assignmentToEdit = lastShownAssignmentList.get(index.getZeroBased());
        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, students, marks);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && model.hasAssignment(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.setAssignment(assignmentToEdit, editedAssignment);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment));
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code assignmentToEdit}
     * edited with {@code List<String> students} and {@code List<Integer> marks}
     */
    private static Assignment createEditedAssignment(Assignment assignmentToEdit,
                                                     List<String> students, List<String> marks) {

        assert assignmentToEdit != null;

        AssignmentName assignmentName = assignmentToEdit.getAssignmentName();
        Assignment editedAssignment = new Assignment(assignmentName);
        editedAssignment.setGrades(students, marks);
        return editedAssignment;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateGradesCommand)) {
            return false;
        }

        // state check
        UpdateGradesCommand e = (UpdateGradesCommand) other;
        return index.equals(e.index)
                   && marks.equals(e.marks);
    }
}

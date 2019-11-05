package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;


/**
 * Updates the grades of an existing assignment in the classroom.
 */
public class UpdateGradesCommand extends Command {

    public static final String COMMAND_WORD = "grades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the grades of the assignment identified "
            + "by the index number of the assignment list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Either input grades for all the students, or input one student's index to overwrite the particular "
            + "student's grades.\n"
            + "Parameters (All Students): "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT_INDEX "
            + PREFIX_MARKS + "GRADE GRADE ... GRADE\n"
            + "Parameters (One Student): "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT_INDEX "
            + PREFIX_STUDENT + "STUDENT_INDEX "
            + PREFIX_MARKS + "GRADE\n"
            + "Example (All Students - for 4 Students): " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT + "1 "
            + PREFIX_MARKS + "20 34 56 87\n"
            + "Example (One Student): " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT + "1 "
            + PREFIX_STUDENT + "2 "
            + PREFIX_MARKS + "60";


    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the classroom.";

    private final Index assignment;
    private final Index student;
    private final List<String> students;
    private List<String> marks;
    private boolean updatingIndividualGrade;
    private final String gradeToUpdate;

    /**
     * @param assignment of the assignment in the filtered assignment list to edit
     * @param marks List of marks
     */
    public UpdateGradesCommand(Index assignment, List<String> marks) {
        requireNonNull(assignment);
        requireNonNull(marks);

        this.assignment = assignment;
        this.student = null;
        this.students = new ArrayList<String>();
        this.marks = marks;
        this.gradeToUpdate = null;
    }

    /**
     * @param assignment of the assignment in the filtered assignment list to edit
     * @param student of the student in the filtered student list to edit
     * @param mark this student's mark
     */
    public UpdateGradesCommand(Index assignment, Index student, String mark) {
        requireNonNull(assignment);
        requireNonNull(student);
        requireNonNull(mark);

        this.assignment = assignment;
        this.student = student;
        this.students = new ArrayList<String>();
        this.gradeToUpdate = mark;
        this.updatingIndividualGrade = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        if (assignment.getZeroBased() >= lastShownAssignmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        } else if (updatingIndividualGrade && student.getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (updatingIndividualGrade) {
            marks = lastShownAssignmentList.get(assignment.getZeroBased()).marksStringListFromGrades();
        }

        Assignment assignmentToEdit;
        Assignment editedAssignment;

        for (Student s : lastShownStudentList) {
            String name = s.getName().fullName;
            students.add(name);
        }

        if (!updatingIndividualGrade && lastShownStudentList.size() != marks.size()) {
            String tooManyInputsError = "Number of inputs should be " + lastShownStudentList.size();
            tooManyInputsError += " for " + lastShownStudentList.size() + " students";
            throw new CommandException(tooManyInputsError);
        } else if (updatingIndividualGrade) {
            marks.set(student.getZeroBased(), gradeToUpdate);
        }

        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        assignmentToEdit = lastShownAssignmentList.get(assignment.getZeroBased());
        editedAssignment = createEditedAssignment(assignmentToEdit, students, marks);

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
        AssignmentDeadline assignmentDeadline = assignmentToEdit.getAssignmentDeadline();
        Assignment editedAssignment = new Assignment(assignmentName, assignmentDeadline);
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
        return assignment.equals(e.assignment)
                   && marks.equals(e.marks);
    }
}

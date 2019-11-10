package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentGrades;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

/**
 * Edits the details of an existing student in the classroom.
 */
public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "editassignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the assignment identified "
            + "by the index number used in the displayed assignment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ASSIGNMENT + "ASSIGNMENT_NAME] "
            + "[" + PREFIX_DEADLINE + "ASSIGNMENT_DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ASSIGNMENT + "Math Homework 2 "
            + PREFIX_DEADLINE + "11/11/2019 1200";


    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the classroom.";

    private final Index index;
    private final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index of the assignment in the filtered assignment list to edit
     * @param editAssignmentDescriptor details to edit the student with
     */
    public EditAssignmentCommand(Index index, EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssignmentDescriptor);

        this.index = index;
        this.editAssignmentDescriptor = new EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToEdit = lastShownList.get(index.getZeroBased());
        Assignment editedAssignment = createEditedAssignment(assignmentToEdit, editAssignmentDescriptor);
        List<Student> studentList = model.getFilteredStudentList();
        for (Student student: studentList) {
            if (editedAssignment.isCompleted()) {
                editedAssignment.addOneCompletedStudentGrade(student.getName().fullName);
            } else {
                editedAssignment.addOneUncompletedStudentGrade(student.getName().fullName);
            }

        }


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
     * edited with {@code editAssignmentDescriptor}.
     */
    private static Assignment createEditedAssignment(Assignment assignmentToEdit,
                                                     EditAssignmentDescriptor editAssignmentDescriptor) {
        assert assignmentToEdit != null;

        AssignmentName updatedAssignmentName = editAssignmentDescriptor.getAssignmentName()
                .orElse(assignmentToEdit.getAssignmentName());
        AssignmentDeadline updatedAssignmentDeadline = editAssignmentDescriptor.getAssignmentDeadline()
                .orElse(assignmentToEdit.getAssignmentDeadline());
        Assignment output = new Assignment(updatedAssignmentName, updatedAssignmentDeadline);
        if (assignmentToEdit.isCompleted()) {
            List<String> studentNames = assignmentToEdit.namesStringListFromGrades();
            List<String> studentsGrades = assignmentToEdit.marksStringListFromGrades();
            output.setGrades(studentNames, studentsGrades);
        }
        return output;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }

        // state check
        EditAssignmentCommand e = (EditAssignmentCommand) other;
        return index.equals(e.index)
                && editAssignmentDescriptor.equals(e.editAssignmentDescriptor);
    }

    /**
     * Stores the details to edit the assignment with. Each non-empty field value will replace the
     * corresponding field value of the assignment.
     */
    public static class EditAssignmentDescriptor {
        private AssignmentName assignmentName;
        private AssignmentDeadline assignmentDeadline;
        private AssignmentGrades assignmentGrades;

        public EditAssignmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
            setAssignmentName(toCopy.assignmentName);
            setAssignmentDeadline(toCopy.assignmentDeadline);
            setAssignmentGrades(toCopy.assignmentGrades);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(assignmentName, assignmentDeadline);
        }

        public void setAssignmentName(AssignmentName assignmentName) {
            this.assignmentName = assignmentName;
        }

        public Optional<AssignmentName> getAssignmentName() {
            return Optional.ofNullable(assignmentName);
        }

        public void setAssignmentDeadline(AssignmentDeadline assignmentDeadline) {
            this.assignmentDeadline = assignmentDeadline;
        }

        public Optional<AssignmentDeadline> getAssignmentDeadline() {
            return Optional.ofNullable(assignmentDeadline);
        }

        public void setAssignmentGrades(AssignmentGrades assignmentGrades) {
            this.assignmentGrades = assignmentGrades;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAssignmentDescriptor)) {
                return false;
            }

            // state check
            EditAssignmentDescriptor e = (EditAssignmentDescriptor) other;

            return getAssignmentName().equals(e.getAssignmentName())
                    && getAssignmentDeadline().equals(e.getAssignmentDeadline());
        }
    }
}

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
import seedu.address.model.student.Student;


/**
 * Edits the details of an existing assignment in the address book.
 */
public class EditAssignmentCommand extends Command {

	public static final String COMMAND_WORD = "editassign";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the scores of the assignment identified "
		                                           + "by the index number of the assignment list. "
		                                           + "Existing values will be overwritten by the input values.\n"
		                                           + "Parameters: INDEX (must be a positive integer) "
		                                           + "GRADE ... GRADE"
		                                           + "Example: " + COMMAND_WORD + " 1 "
		                                           + "g/ 20 34 56 87";


	public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
	public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
	public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book.";

	private final Index index;
	private final List<String> students;
	private final List<Integer> grades;

	/**
	 * @param index of the assignment in the filtered assignment list to edit
	 * @param grades List of grades
	 */
	public EditAssignmentCommand(Index index, List<Integer> grades) {
		requireNonNull(index);
		requireNonNull(grades);

		this.index = index;
		this.students = new ArrayList<String>();
		this.grades = grades;

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

		for (Student s : lastShownStudentList) {
			String name = s.getName().fullName;
			students.add(name);
		}

		Assignment assignmentToEdit = lastShownAssignmentList.get(index.getZeroBased());
		Assignment editedAssignment = createEditedAssignment(assignmentToEdit, students, grades);

		if (!assignmentToEdit.isSameAssignment(editedAssignment) && model.hasAssignment(editedAssignment)) {
			throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
		}

		model.setAssignment(assignmentToEdit, editedAssignment);
		model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
		return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment));
	}

	/**
	 * Creates and returns a {@code Assignment} with the details of {@code assignmentToEdit}
	 * edited with {@code editAssignmentDescriptor}.
	 */
	private static Assignment createEditedAssignment(Assignment assignmentToEdit, List<String> students, List<Integer> grades) {
		assert assignmentToEdit != null;
		assignmentToEdit.setGrades(students, grades);

		return assignmentToEdit;
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
			       && grades.equals(e.grades);
	}
}

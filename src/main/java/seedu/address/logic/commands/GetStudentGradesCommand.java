package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Gets either an individual student's grades for all the assignments, or all the undone assignments.
 */
public class GetStudentGradesCommand extends Command {

    public static final String COMMAND_WORD = "getgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets all the grades of a specific student "
            + "OR all the undone assignemnts in the class.\n"
            + "Parameters (Specific student): STUDENT_INDEX\n"
            + "Parameters (Undone assignments): undone\n"
            + "Example (Specific student): " + COMMAND_WORD + " 2\n"
            + "Example (Undone assignments): " + COMMAND_WORD + " undone.";

    public static final String MESSAGE_SUCCESS_INDIVIDUAL = "Grades of %1$s retrieved: \n%2$s";
    public static final String MESSAGE_SUCCESS_UNDONE = "Undone assignments: \n%1$s";

    private final Index index;
    private final boolean getUndone;

    public GetStudentGradesCommand(Index index) {
        getUndone = false;
        this.index = index;
    }

    public GetStudentGradesCommand() {
        getUndone = true;
        index = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();
        if (!getUndone) {
            if (index.getZeroBased() >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            Student student = lastShownStudentList.get(index.getZeroBased());
            StringBuilder output = new StringBuilder();
            for (Assignment assignment : lastShownAssignmentList) {
                Map<String, String> currentAssignmentGrades = assignment.getGrades();
                if (currentAssignmentGrades.containsKey(student.getName().toString())) {
                    output.append(assignment.getAssignmentName() + ": "
                            + currentAssignmentGrades.get(student.getName().toString()) + "\n");
                }
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_INDIVIDUAL, student.getName().toString(),
                    output.toString()));
        } else {
            StringBuilder output = new StringBuilder();
            for (Assignment assignment : lastShownAssignmentList) {
                Map<String, String> currentAssignmentGrades = assignment.getGrades();
                if (currentAssignmentGrades.containsValue("Did not do")) {
                    output.append(assignment.getAssignmentName().toString() + ": ");
                    boolean foundFirst = false;
                    for (String student : currentAssignmentGrades.keySet()) {
                        if (currentAssignmentGrades.get(student).equals("Did not do")) {
                            if (foundFirst) {
                                output.append(", " + student);
                            } else {
                                output.append(student);
                                foundFirst = true;
                            }
                        }
                    }
                    output.append("\n");
                }
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_UNDONE, output.toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetStudentGradesCommand // instanceof handles nulls
                && index.equals(((GetStudentGradesCommand) other).index));
    }
}

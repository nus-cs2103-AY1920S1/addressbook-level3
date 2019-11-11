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

//@@author weikiat97
/**
 * Gets an individual student's grades for all the assignments.
 */
public class GetStudentGradesCommand extends Command {

    public static final String COMMAND_WORD = "getgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets all the grades of a specific student.\n"
            + "Parameters: STUDENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Grades of %1$s retrieved: \n%2$s";

    private final Index index;

    public GetStudentGradesCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();

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

        return new CommandResult(String.format(MESSAGE_SUCCESS, student.getName().toString(),
                output.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetStudentGradesCommand // instanceof handles nulls
                && index.equals(((GetStudentGradesCommand) other).index));
    }
}

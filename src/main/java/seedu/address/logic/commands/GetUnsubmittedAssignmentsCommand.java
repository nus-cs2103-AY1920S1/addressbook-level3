package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Gets all the unsubmitted assignments in the classroom.
 */
public class GetUnsubmittedAssignmentsCommand extends Command {

    public static final String COMMAND_WORD = "getunsubmitted";
    public static final String UNSUBMITTED = "Not submitted.";
    public static final String MESSAGE_SUCCESS = "Listed all unsubmitted assignments: \n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();

        StringBuilder output = new StringBuilder();
        for (Assignment assignment : lastShownAssignmentList) {
            Map<String, String> currentAssignmentGrades = assignment.getGrades();
            if (currentAssignmentGrades.containsValue(UNSUBMITTED)) {
                output.append(assignment.getAssignmentName().toString() + ": ");
                boolean foundFirst = false;
                for (String student : currentAssignmentGrades.keySet()) {
                    if (currentAssignmentGrades.get(student).equals("Not submitted.")) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, output.toString()));
    }
}

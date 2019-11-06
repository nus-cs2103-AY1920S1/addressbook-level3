package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
//import static seedu.address.model.Model.PREDICATE_SHOW_NO_STUDENTS;

import seedu.address.model.Model;

/**
 * Lists all assignments in the classroom to the user.
 */
public class ListAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "listassignments";

    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        //model.updateFilteredStudentList(PREDICATE_SHOW_NO_STUDENTS);
        model.displayAssignments();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

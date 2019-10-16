package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

import java.util.List;

/**
 * Lists all assignments in the classroom to the user.
 */
public class ListAssignmentCommand extends Command {

	public static final String COMMAND_WORD = "listassign";

	public static final String MESSAGE_SUCCESS = "Listed all assignments";


	@Override
	public CommandResult execute(Model model) {
		requireNonNull(model);
		model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
		List<Assignment> assignmentList = model.getFilteredAssignmentList();
		String output = MESSAGE_SUCCESS + "\n\n";
		for (Assignment assignment:assignmentList) {
			output += assignment.toString() + "\n\n";
		}
		return new CommandResult(output);
	}
}

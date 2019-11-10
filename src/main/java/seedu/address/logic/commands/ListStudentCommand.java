package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.model.Model;

/**
 * Lists all students in the classroom to the user.
 */
public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "liststudents";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.displayStudents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

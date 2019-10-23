package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyClassroom;

/**
 * Undoes a previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous command ";

    public static final String MESSAGE_UNDO_SUCCESS = "Undo success!";
    public static final String MESSAGE_UNDO_FAILURE = "There is no action to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }
        ReadOnlyClassroom previousClassroom = model.undo();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.setClassroom(previousClassroom);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}

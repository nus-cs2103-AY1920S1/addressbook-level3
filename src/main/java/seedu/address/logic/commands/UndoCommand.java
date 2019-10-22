package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

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
        model.undo();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.saveState();
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}

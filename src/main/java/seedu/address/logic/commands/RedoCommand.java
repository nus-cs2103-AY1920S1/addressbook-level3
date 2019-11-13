package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONLISTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyNotebook;

//@@author weikiat97
/**
 * Redoes a previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the previous undone command ";

    public static final String MESSAGE_REDO_SUCCESS = "Redo success!";
    public static final String MESSAGE_REDO_FAILURE = "There is no action to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_REDO_FAILURE);
        }
        ReadOnlyNotebook previousCopy = model.redo();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.updateFilteredLessonWeekList(PREDICATE_SHOW_ALL_LESSONLISTS);
        model.setNotebook(previousCopy);
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }
}

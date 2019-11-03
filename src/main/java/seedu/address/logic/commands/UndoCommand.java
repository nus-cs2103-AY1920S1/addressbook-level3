package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyNotebook;

import static seedu.address.model.Model.*;

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
        ReadOnlyNotebook previousNotebook = model.undo();
        System.out.println("start tracking previous notebook: ");
        previousNotebook.getLessonWeekList().get(0).asUnmodifiableObservableList()
                .forEach(lesson -> System.out.println(lesson));
        System.out.println("start tracking current notebook: ");
        model.getFilteredLessonWeekList().get(0).asUnmodifiableObservableList()
                .forEach(lesson -> System.out.println(lesson));
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.updateFilteredLessonWeekList(PREDICATE_SHOW_ALL_LESSONLISTS);
        model.setNotebook(previousNotebook);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}

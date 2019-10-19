package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

    public static final String MESSAGE_COMMAND_NOT_UNDOABLE =
        "The command \'%1$s\' cannot be stored as an undoable event.";

    /**
     * Generates an Event object that can execute the behaviour of a given Command as well
     * as its opposite behaviour.
     *
     * @param command a {@code Command} to be represented with using an Event object
     * @return an {@code Event} that can be undone or redone
     */
    static Event commandToEvent(UndoableCommand command) throws CommandException {
        if (command instanceof AddExerciseCommand) {
            Exercise exercise = ((AddExerciseCommand) command).getExercise();
            return new AddExerciseEvent(exercise);

        } else if (command instanceof DeleteExerciseCommand) {
            Exercise exercise = ((DeleteExerciseCommand) command).getExercise();
            return new DeleteExerciseEvent(exercise);

        } else if (command instanceof EditCommand) {
            Exercise exerciseOld = ((EditCommand) command).getExerciseToEdit();
            Exercise exerciseNew = ((EditCommand) command).getEditedExercise();
            return new EditEvent(exerciseOld, exerciseNew);

        } else if (command instanceof ClearCommand) {
            ReadOnlyResourceBook<Exercise> exerciseBookCleared =
                new ReadOnlyResourceBook<>(((ClearCommand) command).getExerciseBookCleared());
            return new ClearEvent(exerciseBookCleared);

        } else {
            throw new CommandException(
                String.format(MESSAGE_COMMAND_NOT_UNDOABLE, command.getClass().getName()));
        }
    }

}

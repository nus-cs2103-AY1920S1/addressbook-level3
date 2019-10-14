package seedu.exercise.logic.commands.history;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.exercise.Exercise;

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
     * @param command a command to be represented with using an Event object
     * @return an Event that can be undone or redone
     */
    static Event commandToEvent(UndoableCommand command) throws CommandException {
        if (command instanceof AddExerciseCommand) {
            Exercise exercise = ((AddExerciseCommand) command).getExercise();
            return new AddExerciseEvent(exercise);

        } else if (command instanceof DeleteExerciseCommand) {
            Exercise exercise = ((DeleteExerciseCommand) command).getExercise();
            return new DeleteExerciseEvent(exercise);

        } else {
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, command.getClass().getName()));
        }
    }

}

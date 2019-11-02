package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.DeleteExerciseCommand.MESSAGE_USAGE_EXERCISE;
import static seedu.exercise.logic.commands.DeleteRegimeCommand.MESSAGE_USAGE_REGIME;

/**
 * Represents a DeleteCommand with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command implements UndoableCommand, TypeDependentCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exercise identified by the index number used in the displayed exercise list.\n"
            + "or Deletes the regimes/exercise in regime identified by name/index in the displayed regime list\n"
            + "EXERCISE: " + MESSAGE_USAGE_EXERCISE + "\n"
            + "REGIME: " + MESSAGE_USAGE_REGIME;

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }
}

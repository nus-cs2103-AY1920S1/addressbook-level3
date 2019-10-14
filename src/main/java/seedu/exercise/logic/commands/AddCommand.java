package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.AddExerciseCommand.MESSAGE_USAGE_EXERCISE;
import static seedu.exercise.logic.commands.AddRegimeCommand.MESSAGE_USAGE_REGIME;

/**
 * Represents an AddCommand with hidden internal logic and the ability to be executed.
 */
public abstract class AddCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds exercise to exercise list or adds regime to regime list.\n"
            + "EXERCISE: " + MESSAGE_USAGE_EXERCISE + "\n"
            + "REGIME: " + MESSAGE_USAGE_REGIME;
}

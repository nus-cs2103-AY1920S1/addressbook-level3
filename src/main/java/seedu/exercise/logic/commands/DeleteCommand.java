package seedu.exercise.logic.commands;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Represents a DeleteCommand with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command implements UndoableCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE_EXERCISE = "Parameters: INDEX (must be a positive integer)\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "exercise " + PREFIX_INDEX + "1";

    public static final String MESSAGE_USAGE_REGIME = "Parameters: REGIME NAME, INDEX (must be a positive integer)\n"
            + "\t\tExample: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "regime "
            + PREFIX_NAME + "level 1 " + PREFIX_INDEX + "1";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exercise identified by the index number used in the displayed exercise list.\n"
            + "or Deletes the regimes/exercise in regime identified by name/index in the displayed regime list\n"
            + "EXERCISE: " + MESSAGE_USAGE_EXERCISE + "\n"
            + "REGIME: " + MESSAGE_USAGE_REGIME;
}

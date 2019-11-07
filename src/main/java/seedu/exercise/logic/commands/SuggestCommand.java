package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.SuggestBasicCommand.MESSAGE_USAGE_SUGGEST_BASIC;
import static seedu.exercise.logic.commands.SuggestPossibleCommand.MESSAGE_USAGE_SUGGEST_POSSIBLE;

/**
 * Represents an SuggestCommand with hidden internal logic and the ability to be executed.
 */
public abstract class SuggestCommand extends Command {

    public static final String COMMAND_WORD = "suggest";

    public static final String MESSAGE_SUCCESS = "Listed all suggested exercises";

    public static final String MESSAGE_USAGE = "Suggest: suggests exercises. \n"
            + "BASIC: " + MESSAGE_USAGE_SUGGEST_BASIC + "\n"
            + "POSSIBLE: " + MESSAGE_USAGE_SUGGEST_POSSIBLE;

}

package dukecooks.logic.commands;

import static dukecooks.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;

/**
 * Pushes an item into a storage of DukeCooks
 */
public abstract class PushCommand extends Command {

    public static final String COMMAND_WORD = "push";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pushes an item to a component of Duke Cooks. "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n"
            + "Example: " + COMMAND_WORD + " exercise "
            + "1 " + PREFIX_EXERCISE_INDEX + "1 "
            + PREFIX_SETS + "5 "
            + PREFIX_REPETITIONS + "5";
}

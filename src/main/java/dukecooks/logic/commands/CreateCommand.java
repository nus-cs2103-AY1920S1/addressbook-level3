package dukecooks.logic.commands;

/**
 * Create command in DukeCooks.
 */
public abstract class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an item in a component of Duke Cooks. "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n"
            + "Example: " + COMMAND_WORD + " page ";
}

package dukecooks.logic.commands;

/**
 * Lists all components in DukeCooks to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists entries of a component of Duke Cooks. "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n";
}

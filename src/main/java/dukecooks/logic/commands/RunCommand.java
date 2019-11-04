package dukecooks.logic.commands;

/**
 * Runs an item in DukeCooks
 */

public abstract class RunCommand extends Command {

    public static final String COMMAND_WORD = "run";

    public static final String MESSAGE_USAGE = ": Runs an item of a component of Duke Cooks. "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n";
}

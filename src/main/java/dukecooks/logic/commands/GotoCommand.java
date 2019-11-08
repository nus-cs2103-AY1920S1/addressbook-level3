package dukecooks.logic.commands;

/**
 * Directs to a feature in DukeCooks to the user.
 */
public abstract class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Directs to the given feature. "
            + "Parameters: "
            + COMMAND_WORD
            + "<variant>";


}

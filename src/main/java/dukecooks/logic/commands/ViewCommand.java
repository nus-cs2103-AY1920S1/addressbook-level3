package dukecooks.logic.commands;

/**
 * Views an item in DukeCooks.
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views a component of DukeCooks "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n"
            + "Example: " + COMMAND_WORD + " recipe "
            + "1";
}

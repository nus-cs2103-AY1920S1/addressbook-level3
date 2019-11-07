package unrealunity.visit.logic.commands;

import unrealunity.visit.model.Model;

/**
 * Adds a person to the address book.
 */
public class AliasListCommand extends Command {

    public static final String COMMAND_WORD = "aliaslist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of existing user-defined aliases.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        String aliases = model.getAliases(false);
        return new CommandResult(aliases, false, false, false, false, false, false, false, true);
    }
}

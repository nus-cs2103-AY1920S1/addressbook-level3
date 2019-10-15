package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Adds a recipe to Duke Cooks.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds to a component of Duke Cooks. "
            + "Parameters: "
            + COMMAND_WORD
            + " <variant> <arguments>\n"
            + "Example: " + COMMAND_WORD + " recipe "
            + PREFIX_NAME + "Cheese Omelette "
            + PREFIX_INGREDIENT + "eggs "
            + PREFIX_INGREDIENT + "cheese";
}

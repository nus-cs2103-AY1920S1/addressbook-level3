package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Edits the details of an existing recipe in Duke Cooks.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the component identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: VARIANT INDEX (must be a positive integer) \n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT + "INGREDIENT]...\n"
            + "Example: " + COMMAND_WORD + "<variant> 1 ";
}

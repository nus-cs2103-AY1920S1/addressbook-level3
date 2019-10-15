package seedu.address.logic.commands;

/**
 * Finds and lists all recipes in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all components whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: VARIANT KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " <variant> <keywords>";
}

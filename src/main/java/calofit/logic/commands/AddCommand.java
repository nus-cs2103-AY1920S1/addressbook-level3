package calofit.logic.commands;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.meal.Meal;

import static java.util.Objects.requireNonNull;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Adds a meal to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meal to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New meal added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEAL = "This meal already exists in the address book";

    private final Meal toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Meal}
     */
    public AddCommand(Meal meal) {
        requireNonNull(meal);
        toAdd = meal;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMeal(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        }

        model.addMeal(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

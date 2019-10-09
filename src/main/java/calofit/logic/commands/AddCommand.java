package calofit.logic.commands;

import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Dish;


/**
 * Adds a dish to the dish database.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a dish to the dish database. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New dish added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database";

    private final Dish toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Dish}
     */
    public AddCommand(Dish dish) {
        requireNonNull(dish);
        toAdd = dish;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDish(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        }

        model.addDish(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

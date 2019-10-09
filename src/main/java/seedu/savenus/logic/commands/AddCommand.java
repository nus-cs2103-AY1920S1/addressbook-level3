package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;

/**
 * Adds a food to the $aveNUS menu.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to our menu. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + PREFIX_NAME + "Mala Xiang Guo "
            + PREFIX_PRICE + "6.80 "
            + PREFIX_DESCRIPTION + "Spicy goodness "
            + PREFIX_TAG + "Spicy "
            + PREFIX_TAG + "Healthy ";

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in our menu!";

    private final Food toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public AddCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFood(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.addFood(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

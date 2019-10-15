package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Adds a restaurant to the system.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a restaurant to the restaurant database. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "KFC "
            + PREFIX_LOCATION + "Jurong "
            + PREFIX_TAG + "FastFood";

    public static final String MESSAGE_SUCCESS = "New restaurant added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESTAURANT =
            "This restaurant already exists in the restaurant database";

    private final Restaurant toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public AddCommand(Restaurant restaurant) {
        requireNonNull(restaurant);
        toAdd = restaurant;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasRestaurant(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }
        model.addRestaurant(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

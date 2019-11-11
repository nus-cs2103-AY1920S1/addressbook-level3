package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Eatery;

/**
 * Adds a eatery to the eatery list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_MAIN = COMMAND_WORD + ": Adds a eatery to the eatery list.\n"
            + "Parameters: "
            + PREFIX_NAME + " [name] "
            + PREFIX_ADDRESS + " [address] "
            + PREFIX_CATEGORY + " [category] "
            + "{" + PREFIX_TAG + " [tag]} ...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Fei Fei Wanton Mee "
            + PREFIX_ADDRESS + " 62 Joo Chiat Place "
            + PREFIX_CATEGORY + " Chinese "
            + PREFIX_TAG + " delicious";

    public static final String MESSAGE_USAGE_TODO = COMMAND_WORD + ": Adds a eatery to the todo list.\n"
            + "Parameters: "
            + PREFIX_NAME + " [name] "
            + PREFIX_ADDRESS + " [address] "
            + "{" + PREFIX_TAG + " [tag]} ...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Fei Fei Wanton Mee "
            + PREFIX_ADDRESS + " 62 Joo Chiat Place "
            + PREFIX_TAG + " delicious";

    public static final String MESSAGE_SUCCESS = "Eatery successfully added: %s";
    public static final String MESSAGE_DUPLICATE_EATERY = "This eatery already exists!";

    private final Eatery toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Eatery}
     */
    public AddCommand(Eatery eatery) {
        requireNonNull(eatery);
        toAdd = eatery;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEatery(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EATERY);
        }

        model.addEatery(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

package seedu.guilttrip.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Wish;

/**
 * Adds a wish entry to guiltTrip.
 */
public class AddWishCommand extends Command {

    public static final String COMMAND_WORD = "addWish";

    public static final String MESSAGE_CATEGORY = "Call the command listCategories for the list of Categories.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wish entry to the finance tracker. "
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Shopping "
            + PREFIX_DESC + "Airpods proooo "
            + PREFIX_AMOUNT + "400 "
            + PREFIX_DATE + "2019 11 01 "
            + PREFIX_TAG + "new\n"
            + MESSAGE_CATEGORY;

    public static final String MESSAGE_SUCCESS = "New wish added: %1$s";

    private final Wish toAdd;

    /**
     * Creates an AddWishCommand to add the specified {@code wish}
     */
    public AddWishCommand(Wish wish) {
        requireNonNull(wish);
        toAdd = wish;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(toAdd.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }
        model.addWish(toAdd);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWishCommand // instanceof handles nulls
                && toAdd.equals(((AddWishCommand) other).toAdd));
    }
}

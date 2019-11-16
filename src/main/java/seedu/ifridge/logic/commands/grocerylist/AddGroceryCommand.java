package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;

/**
 * Adds a grocery item to the grocery list.
 */
public class AddGroceryCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food item to the grocery list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: glist " + COMMAND_WORD + " "
            + PREFIX_NAME + "Apples "
            + PREFIX_AMOUNT + "300g "
            + PREFIX_EXPIRY_DATE + "30/11/2019 "
            + PREFIX_TAG + "healthy "
            + PREFIX_TAG + "fruit";

    public static final String MESSAGE_SUCCESS = "New grocery item added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROCERY_ITEM = "This food item already exists in the grocery list";

    private final GroceryItem toAdd;

    /**
     * Creates an AddGroceryCommand to add the specified {@code GroceryItem}
     */
    public AddGroceryCommand(GroceryItem food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroceryItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROCERY_ITEM);
        }

        if (Amount.isEmptyAmount(toAdd.getAmount())) {
            throw new CommandException(Amount.MESSAGE_ZERO_AMOUNT);
        }

        UnitDictionary unitDictionary = model.getUnitDictionary();
        try {
            unitDictionary.checkUnitDictionary(toAdd, model);
        } catch (InvalidUnitException e) {
            throw new CommandException(Amount.MESSAGE_INCORRECT_UNIT);
        }

        model.addGroceryItem(toAdd);
        model.commitGroceryList();
        model.commitWasteList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroceryCommand // instanceof handles nulls
                && toAdd.equals(((AddGroceryCommand) other).toAdd));
    }
}

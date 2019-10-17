package seedu.address.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;
import seedu.address.model.food.ShoppingItem;

/**
 * Edits the details of an existing shopping Item in the shopping list.
 */
public class BoughtShoppingCommand extends Command {

    public static final String COMMAND_WORD = "bought";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the ShoppingItem at the index as bought. "
            + "Also specifies the expiry date and amount of items bought. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EXPIRY_DATE + "EXPIRY_DATE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRY_DATE + "30.04.2019"
            + PREFIX_AMOUNT + "2";

    public static final String MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS = "Edited shopping item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final BoughtShoppingItemDescriptor boughtShoppingItemDescriptor;

    /**
     * @param index of the shopping item in the filtered shopping list to edit
     * @param boughtShoppingItemDescriptor details to edit the shoppingItem with
     */
    public BoughtShoppingCommand(Index index, BoughtShoppingItemDescriptor boughtShoppingItemDescriptor) {
        requireNonNull(index);
        requireNonNull(boughtShoppingItemDescriptor);

        this.index = index;
        this.boughtShoppingItemDescriptor = new BoughtShoppingItemDescriptor(boughtShoppingItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ShoppingItem> lastShownList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
        }

        ShoppingItem shoppingItemToMarkAsBought = lastShownList.get(index.getZeroBased());
        ShoppingItem boughtShoppingItem = createBoughtShoppingItem(shoppingItemToMarkAsBought,
                boughtShoppingItemDescriptor);

        model.setShoppingItem(shoppingItemToMarkAsBought, boughtShoppingItem);
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS, boughtShoppingItem));
    }

    /**
     * Creates and returns a {@code ShoppingItem} with the details of {@code shoppingItemToEdit}
     * edited with {@code boughtShoppingItemDescriptor}.
     */
    private static ShoppingItem createBoughtShoppingItem(ShoppingItem shoppingItemToEdit,
                                                 BoughtShoppingItemDescriptor boughtShoppingItemDescriptor) {
        assert shoppingItemToEdit != null;

        Amount updatedAmount = boughtShoppingItemDescriptor.getAmount().orElse(shoppingItemToEdit.getAmount());
        ExpiryDate updatedExpiryDate = boughtShoppingItemDescriptor.getExpiryDate()
                .orElse(shoppingItemToEdit.getExpiryDate());
        return new ShoppingItem(shoppingItemToEdit.getName(), updatedAmount, updatedExpiryDate);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class BoughtShoppingItemDescriptor {
        private Amount amount;
        private ExpiryDate expiryDate;

        public BoughtShoppingItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public BoughtShoppingItemDescriptor(BoughtShoppingItemDescriptor toCopy) {
            setAmount(toCopy.amount);
            setExpiryDate(toCopy.expiryDate);
            //setExpiryDate(toCopy.expiryDate);
            //setTags(toCopy.tags);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public void setExpiryDate(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }
    }
}

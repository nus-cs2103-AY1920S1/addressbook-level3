package seedu.address.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Amount;
import seedu.address.model.food.Name;
import seedu.address.model.food.ShoppingItem;

//For now edit shopping command will not be able to change the expiry date
/**
 * Edits the details of an existing person in the address book.
 */
public class EditShoppingCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the shopping item identified "
            + "by the index number used in the displayed shopping list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "apple"
            + PREFIX_AMOUNT + "2";

    public static final String MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS = "Edited shopping item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditShoppingItemDescriptor editShoppingItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editShoppingItemDescriptor details to edit the person with
     */
    public EditShoppingCommand(Index index, EditShoppingItemDescriptor editShoppingItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editShoppingItemDescriptor);

        this.index = index;
        this.editShoppingItemDescriptor = new EditShoppingItemDescriptor(editShoppingItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ShoppingItem> lastShownList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOPPING_ITEM_DISPLAYED_INDEX);
        }

        ShoppingItem shoppingItemToEdit = lastShownList.get(index.getZeroBased());
        ShoppingItem editedShoppingItem = createEditedShoppingItem(shoppingItemToEdit, editShoppingItemDescriptor);

        model.setShoppingItem(shoppingItemToEdit, editedShoppingItem);
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_SHOPPING_ITEM_SUCCESS, editedShoppingItem));
    }

    /**
     * Creates and returns a {@code ShoppingItem} with the details of {@code shoppingItemToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static ShoppingItem createEditedShoppingItem(ShoppingItem shoppingItemToEdit,
                                                         EditShoppingItemDescriptor editShoppingItemDescriptor) {
        assert shoppingItemToEdit != null;

        Name updatedName = editShoppingItemDescriptor.getName().orElse(shoppingItemToEdit.getName());
        Amount updatedAmount = editShoppingItemDescriptor.getAmount().orElse(shoppingItemToEdit.getAmount());
        boolean updatedUrgentStatus = editShoppingItemDescriptor.getUrgent().orElse(shoppingItemToEdit.isUrgent());

        return new ShoppingItem(updatedName, updatedAmount, updatedUrgentStatus);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditShoppingItemDescriptor {
        private Name name;
        private Amount amount;
        private boolean bought;
        private boolean urgent;

        public EditShoppingItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditShoppingItemDescriptor(EditShoppingItemDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setBought(toCopy.bought);
            setUrgent(toCopy.urgent);
            //setExpiryDate(toCopy.expiryDate);
            //setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public void setBought(boolean bought) {
            this.bought = bought;
        }

        public void setUrgent(boolean urgent) {
            this.urgent = urgent;
        }
        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public Optional<Boolean> getBought() {
            return Optional.ofNullable(bought);
        }

        public Optional<Boolean> getUrgent() {
            return Optional.ofNullable(urgent);
        }
    }
}

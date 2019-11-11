package seedu.address.inventory.logic.commands;

import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_DUPLICATE;
import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_EDITED_ITEM;

import java.util.Optional;

import seedu.address.inventory.logic.commands.exception.CommandException;
import seedu.address.inventory.logic.parser.exception.InvalidNumberException;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.person.commons.util.CollectionUtil;

/**
 * Edits an item to the inventory list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    private static int id;
    private int index;
    private EditItemDescriptor editItemDescriptor;

    /**
     * Creates an EditCommand to add the specified {@code Transaction}
     */
    public EditCommand(int index, EditItemDescriptor editItemDescriptor) {
        this.index = index;

        this.id = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    public EditItemDescriptor getEditItemDescriptor() {
        return editItemDescriptor;
    }

    @Override
    public CommandResult execute(Model model)
            throws Exception {
        Item itemToEdit = model.findItemByIndex(index);
        Item editedItem = createdEditedItem(itemToEdit, editItemDescriptor);

        if (itemToEdit.equals(editedItem) && model.hasItemInInventory(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        if (editedItem.getTotalCost() >= 10000 || editedItem.getSubtotal() >= 10000) {
            throw new InvalidNumberException(InventoryMessages.MESSAGE_TOTAL_TOO_LARGE);
        }

        model.setItem(Integer.parseInt(itemToEdit.getId()), editedItem);
        return new CommandResult(String.format(MESSAGE_EDITED_ITEM, itemToEdit, editedItem));
    }

    /**
     * Edits an item using EditItemDescriptor.
     */
    private static Item createdEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor) {
        String updatedDescription = editItemDescriptor.getDescription().orElse(itemToEdit.getDescription());
        String updatedCategory = editItemDescriptor.getCategory().orElse(itemToEdit.getCategory());
        Integer updatedQuantity = editItemDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        Double updatedCost = editItemDescriptor.getCost().orElse(itemToEdit.getCost());
        Double updatedPrice = editItemDescriptor.getPrice().orElse(itemToEdit.getPrice());
        return new Item(updatedDescription, updatedCategory, updatedQuantity, updatedCost,
                updatedPrice, id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && index == (((EditCommand) other).index))
                && editItemDescriptor.equals(((EditCommand) other).getEditItemDescriptor());
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditItemDescriptor {
        private String description;
        private String category;
        private Integer quantity;
        private Double cost;
        private Double price;


        public EditItemDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setDescription(toCopy.description);
            setCategory(toCopy.category);
            setQuantity(toCopy.quantity);
            setCost(toCopy.cost);
            setPrice(toCopy.price);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, category, quantity, cost, price);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Optional<String> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Optional<Integer> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setCost(Double cost) {
            this.cost = cost;
        }

        public Optional<Double> getCost() {
            return Optional.ofNullable(cost);
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Optional<Double> getPrice() {
            return Optional.ofNullable(price);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditItemDescriptor)) {
                return false;
            }

            // state check
            EditItemDescriptor e = (EditItemDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getCategory().equals(e.getCategory())
                    && getQuantity().equals(e.getQuantity())
                    && getCost().equals(e.getCost())
                    && getPrice().equals(e.getPrice());
        }
    }
}

package seedu.address.inventory.commands;

import java.util.Optional;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.person.commons.util.CollectionUtil;
import seedu.address.person.logic.commands.exceptions.CommandException;

/**
 * Edits an item to the inventory list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_DUPLICATE = "The given input is the same as that of item specified.";
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

    @Override
    public CommandResult execute(Model model)
            throws Exception {
        InventoryMessages inventoryMessages = new InventoryMessages();
        Item itemToEdit = model.findItemByIndex(index);

        Item editedItem = createdEditedItem(itemToEdit, editItemDescriptor);

        if (itemToEdit.equals(editedItem) && model.hasItemInInventory(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }
        model.setItem(Integer.parseInt(itemToEdit.getId()), editedItem);
        return new CommandResult(InventoryMessages.editedItem(itemToEdit, editedItem));
    }

    /**
     * Edits an item using EditItemDescriptor.
     */
    private static Item createdEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor) {
        String updatedDescription = editItemDescriptor.getDescription().orElse(itemToEdit.getDescription());
        String updatedCategory = editItemDescriptor.getCategory().orElse(itemToEdit.getCategory());
        int updatedQuantity = editItemDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        double updatedCost = editItemDescriptor.getCost().orElse(itemToEdit.getCost());
        double updatedPrice = editItemDescriptor.getPrice().orElse(itemToEdit.getPrice());
        return new Item(updatedDescription, updatedCategory, updatedQuantity, updatedCost,
                updatedPrice, id);
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditItemDescriptor {
        private String description;
        private String category;
        private int quantity;
        private double cost;
        private double price;


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

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Optional<Integer> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public Optional<Double> getCost() {
            return Optional.ofNullable(cost);
        }

        public void setPrice(double price) {
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

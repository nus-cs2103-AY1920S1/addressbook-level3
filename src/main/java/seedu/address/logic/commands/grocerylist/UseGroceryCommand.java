package seedu.address.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROCERY_ITEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class UseGroceryCommand extends Command {

    public static final String COMMAND_WORD = "use";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reduces the amount of the grocery item identified "
            + "by the index number used in the displayed grocery list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "Example: glist " + COMMAND_WORD + " 1 a/100g";

    public static final String MESSAGE_USE_GROCERY_ITEM_SUCCESS = "Used grocery item: %1$s";
    public static final String MESSAGE_NOT_USED = "At least amount field must be provided.";

    private final Index index;
    private final UseGroceryItemDescriptor useGroceryItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param useGroceryItemDescriptor details to edit the person with
     */
    public UseGroceryCommand(Index index, UseGroceryItemDescriptor useGroceryItemDescriptor) {
        requireNonNull(index);
        requireNonNull(useGroceryItemDescriptor);

        this.index = index;
        this.useGroceryItemDescriptor = new UseGroceryItemDescriptor(useGroceryItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GroceryItem> lastShownList = model.getFilteredGroceryItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROCERY_ITEM_DISPLAYED_INDEX);
        }

        GroceryItem groceryItemToUse = lastShownList.get(index.getZeroBased());
        GroceryItem usedGroceryItem = createdUsedGroceryItem(groceryItemToUse, useGroceryItemDescriptor);

        model.setGroceryItem(groceryItemToUse, usedGroceryItem);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
        return new CommandResult(String.format(MESSAGE_USE_GROCERY_ITEM_SUCCESS, usedGroceryItem));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static GroceryItem createdUsedGroceryItem(GroceryItem groceryItemToUse,
                                                        UseGroceryItemDescriptor useGroceryItemDescriptor) {
        assert groceryItemToUse != null;

        Name name = groceryItemToUse.getName();
        Amount updatedAmount = groceryItemToUse.getAmount().reduceBy(useGroceryItemDescriptor.amount);
        ExpiryDate expiryDate = groceryItemToUse.getExpiryDate();
        Set<Tag> tags = groceryItemToUse.getTags();

        return new GroceryItem(name, updatedAmount, expiryDate, tags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UseGroceryItemDescriptor {
        private Name name;
        private Amount amount;
        private ExpiryDate expiryDate;
        private Set<Tag> tags;

        public UseGroceryItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UseGroceryItemDescriptor(UseGroceryItemDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setExpiryDate(toCopy.expiryDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAmountUsed() {
            return (Amount.getValue(amount) != 0);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public void setExpiryDate(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }
    }
}

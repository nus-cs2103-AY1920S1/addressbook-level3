package seedu.address.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
public class UseCommand extends Command {

    public static final String COMMAND_WORD = "use";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reduces the amount food item identified "
            + "by the index number used in the displayed grocery list. "
            + "Existing amount will be subtracted by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "Example: " + COMMAND_WORD + " 1 300g";

    public static final String MESSSGE_USE_GROCERY_ITEM_SUCCESS = "Used food item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final UseGroceryItemDescriptor useGroceryItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param useGroceryItemDescriptor details to edit the person with
     */
    public UseCommand(Index index, UseGroceryItemDescriptor useGroceryItemDescriptor) {
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        GroceryItem foodToEdit = lastShownList.get(index.getZeroBased());
        GroceryItem editedFood = createEditedGroceryItem(foodToEdit, useGroceryItemDescriptor);

        model.setGroceryItem(foodToEdit, editedFood);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSSGE_USE_GROCERY_ITEM_SUCCESS, editedFood));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static GroceryItem createEditedGroceryItem(GroceryItem foodToEdit,
                                                       UseGroceryItemDescriptor useGroceryItemDescriptor) {
        assert foodToEdit != null;

        Name updatedName = useGroceryItemDescriptor.getName().orElse(foodToEdit.getName());
        Amount updatedAmount = useGroceryItemDescriptor.getAmount().orElse(foodToEdit.getAmount());
        ExpiryDate updatedExpiryDate = useGroceryItemDescriptor.getExpiryDate().orElse(foodToEdit.getExpiryDate());
        Set<Tag> updatedTags = useGroceryItemDescriptor.getTags().orElse(foodToEdit.getTags());

        return new GroceryItem(updatedName, updatedAmount, updatedExpiryDate, updatedTags);
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
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags);
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

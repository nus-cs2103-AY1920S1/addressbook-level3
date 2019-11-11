package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_GROCERY_ITEMS;
import static seedu.ifridge.model.food.Amount.MESSAGE_INCORRECT_UNIT;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.commons.util.CollectionUtil;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;
import seedu.ifridge.model.tag.Tag;

/**
 * Edits the details of an existing grocery item in the grocery list.
 */
public class EditGroceryCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the grocery item identified "
            + "by the index number used in the displayed grocery list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EXPIRY_DATE + "EXPIRY_DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: glist " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Pisang Goreng "
            + PREFIX_TAG + "fried";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited grocery item: %1$s";
    public static final String MESSAGE_NOT_EDITED =
            "At least one different field (name, expiry date, or tag) must be provided.";
    public static final String MESSAGE_DUPLICATE_GROCERY_ITEM = "This food item already exists in the grocery list";

    private final Index index;
    private final EditGroceryItemDescriptor editGroceryItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editGroceryItemDescriptor details to edit the person with
     */
    public EditGroceryCommand(Index index, EditGroceryItemDescriptor editGroceryItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editGroceryItemDescriptor);

        this.index = index;
        this.editGroceryItemDescriptor = new EditGroceryItemDescriptor(editGroceryItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GroceryItem> lastShownList = model.getFilteredGroceryItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROCERY_ITEM_DISPLAYED_INDEX);
        }

        GroceryItem groceryItemToEdit = lastShownList.get(index.getZeroBased());
        GroceryItem editedGroceryItem = createdEditedGroceryItem(groceryItemToEdit, editGroceryItemDescriptor);

        if (groceryItemToEdit.isSameFood(editedGroceryItem)) { // if changes is done on the same index
            // even tag also not edited
            if (groceryItemToEdit.equals(editedGroceryItem)) {
                throw new CommandException(MESSAGE_NOT_EDITED);
            }
            // continue if other fields are the same and tag is edited
        } else if (model.hasGroceryItem(editedGroceryItem)) { // if changes done on different index
            throw new CommandException(MESSAGE_DUPLICATE_GROCERY_ITEM);
        }

        UnitDictionary unitDictionary = model.getUnitDictionary();
        try {
            unitDictionary.checkUnitDictionary(editedGroceryItem, model);
        } catch (InvalidUnitException e) {
            throw new CommandException(MESSAGE_INCORRECT_UNIT);
        }

        model.setGroceryItem(groceryItemToEdit, editedGroceryItem);
        model.commitGroceryList();
        model.commitWasteList();
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedGroceryItem));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static GroceryItem createdEditedGroceryItem(GroceryItem groceryItemToEdit,
                                                        EditGroceryItemDescriptor editGroceryItemDescriptor)
        throws InvalidUnitException {
        assert groceryItemToEdit != null;

        Name updatedName = editGroceryItemDescriptor.getName().orElse(groceryItemToEdit.getName());
        Amount updatedAmount = editGroceryItemDescriptor.getAmount().orElse(groceryItemToEdit.getAmount());
        ExpiryDate updatedExpiryDate = editGroceryItemDescriptor.getExpiryDate()
                .orElse(groceryItemToEdit.getExpiryDate());
        Set<Tag> updatedTags = editGroceryItemDescriptor.getTags().orElse(groceryItemToEdit.getTags());

        return new GroceryItem(updatedName, updatedAmount, updatedExpiryDate, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditGroceryItemDescriptor {
        private Name name;
        private Amount amount;
        private ExpiryDate expiryDate;
        private Set<Tag> tags;

        public EditGroceryItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGroceryItemDescriptor(EditGroceryItemDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setExpiryDate(toCopy.expiryDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, expiryDate, tags);
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

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof EditGroceryItemDescriptor // instanceof handles nulls
                    && name.equals(((EditGroceryItemDescriptor) other).name)
                    && amount.equals(((EditGroceryItemDescriptor) other).amount)
                    && expiryDate.equals(((EditGroceryItemDescriptor) other).expiryDate)
                    && tags.equals(((EditGroceryItemDescriptor) other).tags));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditGroceryCommand // instanceof handles nulls
                && index.equals(((EditGroceryCommand) other).index)
                && editGroceryItemDescriptor.equals(((EditGroceryCommand) other).editGroceryItemDescriptor));
    }
}

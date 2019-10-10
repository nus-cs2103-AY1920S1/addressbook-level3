package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.savenus.model.Model.PREDICATE_SHOW_ALL_FOOD;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.commons.util.CollectionUtil;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.tag.Tag;

/**
 * Edits the details of an existing food in the $aveNUS menu.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the food identified "
            + "by the index number used in the displayed food list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_OPENING_HOURS + "OPENING HOURS] "
            + "[" + PREFIX_RESTRICTIONS + "RESTRICTIONS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE + "3.99 "
            + PREFIX_DESCRIPTION + "Japanese Noodle with Curry";

    public static final String MESSAGE_EDIT_FOOD_SUCCESS = "Edited Food: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field of the food to be edited must be provided.";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the menu.";

    private final Index index;
    private final EditFoodDescriptor editFoodDescriptor;

    /**
     * @param index of the food in the filtered food list to edit
     * @param editFoodDescriptor details to edit the food with
     */
    public EditCommand(Index index, EditFoodDescriptor editFoodDescriptor) {
        requireNonNull(index);
        requireNonNull(editFoodDescriptor);

        this.index = index;
        this.editFoodDescriptor = new EditFoodDescriptor(editFoodDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToEdit = lastShownList.get(index.getZeroBased());
        Food editedFood = createEditedFood(foodToEdit, editFoodDescriptor);

        if (!foodToEdit.isSameFood(editedFood) && model.hasFood(editedFood)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.setFood(foodToEdit, editedFood);
        model.updateFilteredFoodList(PREDICATE_SHOW_ALL_FOOD);
        return new CommandResult(String.format(MESSAGE_EDIT_FOOD_SUCCESS, editedFood));
    }

    /**
     * Creates and returns a {@code Food} with the details of {@code foodToEdit}
     * edited with {@code editFoodDescriptor}.
     */
    private static Food createEditedFood(Food foodToEdit, EditFoodDescriptor editFoodDescriptor) {
        assert foodToEdit != null;

        Name updatedName = editFoodDescriptor.getName().orElse(foodToEdit.getName());
        Price updatedPrice = editFoodDescriptor.getPrice().orElse(foodToEdit.getPrice());
        Description updatedDescription = editFoodDescriptor.getDescription().orElse(foodToEdit.getDescription());
        Category updatedCategory = editFoodDescriptor.getCategory().orElse(foodToEdit.getCategory());
        Set<Tag> updatedTags = editFoodDescriptor.getTags().orElse(foodToEdit.getTags());
        Location updatedLocation = editFoodDescriptor.getLocation().orElse(foodToEdit.getLocation());
        OpeningHours updatedOpeningHours = editFoodDescriptor.getOpeningHours().orElse(foodToEdit.getOpeningHours());
        Restrictions updatedRestrictions = editFoodDescriptor.getRestrictions().orElse(foodToEdit.getRestrictions());

        return new Food(updatedName, updatedPrice, updatedDescription,
                updatedCategory, updatedTags, updatedLocation, updatedOpeningHours, updatedRestrictions);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFoodDescriptor.equals(e.editFoodDescriptor);
    }

    /**
     * Stores the details to edit the food with. Each non-empty field value will replace the
     * corresponding field value of the food.
     */
    public static class EditFoodDescriptor {
        private Name name;
        private Price price;
        private Description description;
        private Category category;
        private Set<Tag> tags;
        private Location location;
        private OpeningHours openingHours;
        private Restrictions restrictions;

        public EditFoodDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFoodDescriptor(EditFoodDescriptor toCopy) {
            setName(toCopy.name);
            setPrice(toCopy.price);
            setDescription(toCopy.description);
            setCategory(toCopy.category);
            setTags(toCopy.tags);
            setLocation(toCopy.location);
            setOpeningHours(toCopy.openingHours);
            setRestrictions(toCopy.restrictions);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, price, description, tags, location);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public Optional<OpeningHours> getOpeningHours() {
            return Optional.ofNullable(openingHours);
        }

        public void setRestrictions(Restrictions restrictions) {
            this.restrictions = restrictions;
        }

        public Optional<Restrictions> getRestrictions() {
            return Optional.ofNullable(restrictions);
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
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFoodDescriptor)) {
                return false;
            }

            // state check
            EditFoodDescriptor e = (EditFoodDescriptor) other;

            return getName().equals(e.getName())
                    && getPrice().equals(e.getPrice())
                    && getDescription().equals(e.getDescription())
                    && getCategory().equals(e.getCategory())
                    && getTags().equals(e.getTags())
                    && getLocation().equals(e.getLocation())
                    && getOpeningHours().equals(e.getOpeningHours())
                    && getRestrictions().equals(e.getRestrictions());
        }
    }
}

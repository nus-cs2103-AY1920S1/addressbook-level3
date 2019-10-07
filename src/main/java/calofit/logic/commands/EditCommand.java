package calofit.logic.commands;

import calofit.logic.commands.exceptions.CommandException;
import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.commons.util.CollectionUtil;
import calofit.model.Model;
import calofit.model.meal.Meal;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static calofit.model.Model.PREDICATE_SHOW_ALL_MEALS;

/**
 * Edits the details of an existing meal in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meal identified "
            + "by the index number used in the displayed meal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_MEAL_SUCCESS = "Edited Meal: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEAL = "This meal already exists in the address book.";

    private final Index index;
    private final EditMealDescriptor editMealDescriptor;

    /**
     * @param index of the meal in the filtered meal list to edit
     * @param editMealDescriptor details to edit the meal with
     */
    public EditCommand(Index index, EditMealDescriptor editMealDescriptor) {
        requireNonNull(index);
        requireNonNull(editMealDescriptor);

        this.index = index;
        this.editMealDescriptor = new EditMealDescriptor(editMealDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meal> lastShownList = model.getFilteredMealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
        }

        Meal mealToEdit = lastShownList.get(index.getZeroBased());
        Meal editedMeal = createEditedMeal(mealToEdit, editMealDescriptor);

        if (!mealToEdit.isSameMeal(editedMeal) && model.hasMeal(editedMeal)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        }

        model.setMeal(mealToEdit, editedMeal);
        model.updateFilteredMealList(PREDICATE_SHOW_ALL_MEALS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEAL_SUCCESS, editedMeal));
    }

    /**
     * Creates and returns a {@code Meal} with the details of {@code mealToEdit}
     * edited with {@code editMealDescriptor}.
     */
    private static Meal createEditedMeal(Meal mealToEdit, EditMealDescriptor editMealDescriptor) {
        assert mealToEdit != null;

        Name updatedName = editMealDescriptor.getName().orElse(mealToEdit.getName());
        Set<Tag> updatedTags = editMealDescriptor.getTags().orElse(mealToEdit.getTags());

        return new Meal(updatedName, updatedTags);
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
                && editMealDescriptor.equals(e.editMealDescriptor);
    }

    /**
     * Stores the details to edit the meal with. Each non-empty field value will replace the
     * corresponding field value of the meal.
     */
    public static class EditMealDescriptor {
        private Name name;
        private Set<Tag> tags;

        public EditMealDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMealDescriptor(EditMealDescriptor toCopy) {
            setName(toCopy.name);
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

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
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
            if (!(other instanceof EditMealDescriptor)) {
                return false;
            }

            // state check
            EditMealDescriptor e = (EditMealDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
